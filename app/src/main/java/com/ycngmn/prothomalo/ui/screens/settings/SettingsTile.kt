package com.ycngmn.prothomalo.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.ui.assets.AppFont

@Composable
fun SettingTile(iconRes: Int, title: String, subtitle: String, modifier: Modifier = Modifier ) {

    Row(
        modifier = modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(id = iconRes), contentDescription = title,
            modifier = Modifier.padding(end = 30.dp).size(30.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Column {
            Text(
                text = title,
                style = AppFont.bottomSheetTS,
            )
            Text(
                text = subtitle,
                style = AppFont.dateTS.copy(fontSize = 15.sp),
            )
        }
    }
}