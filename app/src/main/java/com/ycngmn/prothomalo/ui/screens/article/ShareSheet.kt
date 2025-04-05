package com.ycngmn.prothomalo.ui.screens.article

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.NewsContainer
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily
import com.ycngmn.prothomalo.utils.createPdf
import com.ycngmn.prothomalo.utils.sharePdf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareSheet(news: NewsContainer) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    val closeSheet: () -> Unit = {
        scope.launch { bottomSheetState.hide() }
            .invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    openBottomSheet = false
                }
            }
    }

    // App content
    Icon(
        painter = painterResource(R.drawable.share_windows_40px),
        contentDescription = "Share article",
        modifier = Modifier.size(25.dp)
            .clickable { openBottomSheet = !openBottomSheet },
        tint = MaterialTheme.colorScheme.onBackground,
    )

    // Sheet content

    val context = LocalContext.current
    val copyToClipboard: (String) -> Unit = {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("url", it)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }
    val sharePdf: () -> Unit = {
        scope.launch {
            Toast.makeText(context, "Creating PDF...", Toast.LENGTH_SHORT).show()
            val file = withContext(Dispatchers.IO) { createPdf(news, context) }
            if (file != null) {
                sharePdf(context, file)
            } else
                Toast.makeText(context, "Failed to create PDF", Toast.LENGTH_SHORT).show()

        }
           }

    if (openBottomSheet) {

        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            Row(
                Modifier.background(MaterialTheme.colorScheme.background).fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row (
                    modifier = Modifier
                        .clickable {
                            copyToClipboard(news.newsUrl)
                            closeSheet()
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.content_copy_24px),
                        contentDescription = "Close",
                        modifier = Modifier.padding(end = 10.dp),
                        tint = MaterialTheme.colorScheme.onBackground)
                    Text(
                        "Copy URL",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontFamily = ShurjoFamily
                    )
                }

                VerticalDivider(modifier = Modifier.height(25.dp))

                Row (
                    modifier = Modifier
                    .clickable {sharePdf(); closeSheet() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.picture_as_pdf_24px),
                        contentDescription = "Share as pdf",
                        modifier = Modifier.padding(end = 10.dp),
                        tint = MaterialTheme.colorScheme.onBackground)
                    Text(
                        "Share as pdf",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontFamily = ShurjoFamily,
                    )
                }
            }

        }
    }
}