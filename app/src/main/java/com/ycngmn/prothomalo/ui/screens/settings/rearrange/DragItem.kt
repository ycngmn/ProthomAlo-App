package com.ycngmn.prothomalo.ui.screens.settings.rearrange

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.ui.assets.FontSizes
import com.ycngmn.prothomalo.ui.assets.TextStyles

@Composable
internal fun DragItem(
    item: String,
    isChecked: Boolean,
    isEnabled: Boolean,
    elevation: Dp,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.clickable { } // To add ripples effect.
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                enabled = isEnabled,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                item,
                modifier = Modifier.padding(start = 20.dp).weight(0.8f),
                fontSize = FontSizes.rearrangeSection,
                style = TextStyles.defaultTS()
            )
            Icon(painterResource(R.drawable.drag_handle_24px), null, Modifier.padding(end = 16.dp))
        }

        if (PaloGlobal.isDarkTheme) HorizontalDivider()

    }
}