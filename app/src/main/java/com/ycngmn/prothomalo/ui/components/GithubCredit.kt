package com.ycngmn.prothomalo.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.ui.assets.FontSizes
import com.ycngmn.prothomalo.ui.assets.Strings
import com.ycngmn.prothomalo.ui.assets.TextStyles

@Composable
fun GithubCredit() {

    val context = LocalContext.current

    Row (
        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
            .clickable {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "https://github.com/ycngmn/ProthomAlo-App".toUri()
                )
                context.startActivity(intent)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(id = R.drawable.github_logo),
            Strings.get("about"),
            Modifier.size(35.dp),
        )
        Column (modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = "Visit us at Github ❤\uFE0F",
                fontSize = FontSizes.githubCreditTitle,
                style = TextStyles.githubCreditTitle,
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = "Report bugs, suggest, contribute",
                fontSize = FontSizes.gitCreditSub,
                style = TextStyles.gitCreditSub,
                modifier = Modifier.padding(end = 16.dp)
            )
        }

    }
}