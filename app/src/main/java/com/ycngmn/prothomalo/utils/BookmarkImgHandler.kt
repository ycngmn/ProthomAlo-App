package com.ycngmn.prothomalo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.ycngmn.prothomalo.prothomalo.NewsContainer
import java.io.File

suspend fun urlToBitmap(context: Context, imageUrl: String): Bitmap? {

    val imageLoader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .allowHardware(false) // Required for converting to bitmap
        .build()

    val result = (imageLoader.execute(request) as? SuccessResult)?.drawable
    return (result as? BitmapDrawable)?.bitmap
}

suspend fun downloadAndSaveImage(context: Context, imageUrl: String, subDir: String, fileName: String): File? {

    val bitmap = urlToBitmap(context, imageUrl) ?: return null

    val directory = File(context.filesDir, subDir)
    if (!directory.exists()) directory.mkdirs()

    val file = File(directory, fileName)
    file.outputStream().use { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    }
    return file
}

suspend fun downloadNewsImages(newsContainer: NewsContainer, context: Context) {
    val subDir = newsContainer.newsUrl.substringAfterLast("/")
    newsContainer.body.forEachIndexed { index, (_, imageUrl) ->
        downloadAndSaveImage(context,imageUrl, subDir, "$index.png")
    }
}

fun loadSavedImage(context: Context, subDir: String, fileName: String): String? {
    val directory = File(context.filesDir, subDir)
    if (!directory.exists()) return null
    val file = File(directory, fileName)
    return if (file.exists()) file.absolutePath else null
}

fun deleteSavedImages(context: Context, subDir: String): Boolean {
    val directory = File(context.filesDir, subDir)
    return directory.deleteRecursively()
}

