package com.ycngmn.prothomalo.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.text.Html
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.graphics.scale
import androidx.core.graphics.withTranslation
import com.ycngmn.prothomalo.prothomalo.NewsContainer
import java.io.File
import java.io.FileOutputStream

suspend fun createPdf(news: NewsContainer, context: Context): File? {
    val document = PdfDocument()
    val pageWidth = 595
    val pageHeight = 842
    val horizontalPadding = 48f // 16.dp ≈ 48px
    val usableWidth = pageWidth - (horizontalPadding * 2)
    val topMargin = 50f
    val bottomMargin = 24f
    val textPaint = TextPaint().apply {
        isAntiAlias = true
        color = Color.BLACK
    }

    var currentY = topMargin
    var pageNumber = 1
    var page = document.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create())
    var canvas = page.canvas

    fun checkAndAddPage(heightNeeded: Float) {
        if (currentY + heightNeeded >= pageHeight - bottomMargin) {
            document.finishPage(page)
            pageNumber++
            page = document.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create())
            canvas = page.canvas
            currentY = topMargin
        }
    }

    fun drawMultilineText(text: String, textSize: Float, color: Int) {
        textPaint.textSize = textSize
        textPaint.color = color

        val staticLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, usableWidth.toInt())
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(0f, 1.2f)
            .setIncludePad(false)
            .build()

        checkAndAddPage(staticLayout.height.toFloat())
        canvas.withTranslation(horizontalPadding, currentY) {
            staticLayout.draw(this)
        }
        currentY += staticLayout.height + 16f
    }

    // Draw headline
    drawMultilineText(news.headline, 25f, Color.BLACK)

    if (news.summary != "null") {
        drawMultilineText(news.summary ?: "", 18f, Color.GRAY)
    }

    if (news.author != "null") {
        val authorText = news.author + (if (news.authorLocation != "null") ", ${news.authorLocation}" else "")
        drawMultilineText(authorText, 17f, Color.BLACK)
    }

    drawMultilineText(FormatTime.toFormattedDate(news.date, news.newsUrl), 17f, Color.BLACK)

    // Divider
    checkAndAddPage(10f)
    val dividerY = currentY
    canvas.drawLine(horizontalPadding, dividerY, pageWidth - horizontalPadding, dividerY, Paint().apply {
        color = Color.GRAY
        strokeWidth = 3f
    })
    currentY += 20f

    // Body
    news.body.forEach { content ->
        when (content.first) {
            "text" -> {
                val cleanHtml = content.second.replace("</p><p>", "<br><br>")
                    .replace(Regex("^<p>|</p>$"), "") + "<br>"
                val text = Html.fromHtml(cleanHtml, Html.FROM_HTML_MODE_LEGACY).toString()
                drawMultilineText(text, 18f, Color.BLACK)
            }
            "image" -> {
                if (content.second.isNotEmpty()) {
                    val bitmap = urlToBitmap(context, content.second) ?: return@forEach
                    val scaled = bitmap.scale(
                        usableWidth.toInt(),
                        (bitmap.height * usableWidth / bitmap.width).toInt()
                    )
                    checkAndAddPage(scaled.height.toFloat())
                    canvas.drawBitmap(scaled, horizontalPadding, currentY, null)
                    currentY += scaled.height + 16f
                }
            }
            "caption" -> {
                if (content.second != "null") {
                    drawMultilineText(content.second, 15f, Color.GRAY)
                }
            }
            "youtube" -> {
                if (content.second.contains("https://www.youtube.com/embed/")) {
                    drawMultilineText("YouTube Video: ${content.second}", 18f, Color.BLACK)
                }
            }
        }
    }

    val urlText = news.newsUrl
    drawMultilineText(urlText, 15f, Color.BLUE)

    document.finishPage(page)

    // Save file
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "${news.headline}.pdf")
    try {
        document.writeTo(FileOutputStream(file))
    } catch (e: Exception) {
        Log.e("PDF Creation", "Failed to write PDF", e)
        return null
    }
    document.close()
    return file
}



fun sharePdf(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Share PDF using"))
}

fun cleanUpPdfs(context: Context) {
    val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    dir?.listFiles()?.forEach { file ->
        if (file.name.endsWith(".pdf")) {
            file.delete()
        }
    }
}
