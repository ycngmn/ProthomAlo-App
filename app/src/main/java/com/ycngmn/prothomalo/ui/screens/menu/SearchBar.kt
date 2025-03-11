package com.ycngmn.prothomalo.ui.screens.menu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.theme.PaloBlue


@Composable
fun PaloSeachBar(
    isSearchFilterVisible: Boolean,
    onClick: () -> Unit,

) {

    BackHandler(isSearchFilterVisible) {
        onClick()
    }


    var query by remember { mutableStateOf("") }

    Box (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))) {
        TextField(
            value = query,
            textStyle = TextStyle(
                fontFamily = ShurjoFamily,
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            onValueChange = {
                query = it
                if (!isSearchFilterVisible)
                    onClick()
                else if (query.isEmpty())
                    onClick()
                            },
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
                modifier = Modifier.clickable {  }
            )
        }

    }
}
@Preview(showBackground = true)
@Composable
fun FilterBar() {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {

        Column (modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "আরও নির্দিষ্ট করে খুঁজুন",
                    fontFamily = ShurjoFamily,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
            }

            TextField(
                value = "",
                onValueChange = {},
                singleLine = true,
                placeholder = { Text("তারিখ") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent)
            )


        }
    }
}