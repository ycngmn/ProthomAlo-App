package com.ycngmn.prothomalo.ui.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.theme.PaloBlue


@Composable
fun PaloSeachBar(onClick: () -> Unit) {

    var query by remember { mutableStateOf("") }

    Box (modifier = Modifier.fillMaxWidth().padding(8.dp)
        .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))) {
        TextField(
            value = query,
            textStyle = TextStyle(
                fontFamily = ShurjoFamily,
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            onValueChange = { query = it },
            singleLine = true,
            placeholder = {
                Text("যা খুঁজতে চান",fontFamily = ShurjoFamily,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
            )
        )

        Box( modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 12.dp)
            .size(30.dp)
            .background(color = PaloBlue, shape = RoundedCornerShape(3.dp)),
            contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.clickable { onClick() }
            )
        }

    }
}
@Preview(showBackground = true)
@Composable
fun FilterBar() {
    Card (
        modifier = Modifier.fillMaxWidth().zIndex(2f).fillMaxHeight()

        , shape = RoundedCornerShape(3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = "আরও নির্দিষ্ট করে খুঁজুন",
                fontFamily = ShurjoFamily,
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )

            Spacer(Modifier.weight(1f))

            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}