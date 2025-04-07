package com.ycngmn.prothomalo.ui.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ycngmn.prothomalo.ui.assets.FontSizes
import com.ycngmn.prothomalo.ui.assets.TextStyles
import com.ycngmn.prothomalo.ui.theme.RealPaloRead

@Composable
fun SectionCards(iconRes: Int, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(16.dp).clickable { onClick() },
        shape = RoundedCornerShape(3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "",
                colorFilter = ColorFilter.tint(RealPaloRead),
                modifier = Modifier.padding(end = 6.dp).size(22.dp)
            )
            Text(
                text = text,
                fontSize = FontSizes.sectionCard,
                style = TextStyles.sectionCard,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}