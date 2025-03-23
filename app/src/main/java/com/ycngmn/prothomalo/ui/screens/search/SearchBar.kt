package com.ycngmn.prothomalo.ui.screens.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.theme.PaloBlue


@Composable
fun PaloSearchBar(
    isSearchFilterVisible: Boolean,
    searchViewModel: SearchViewModel,
    onBackPress: () -> Unit,
    onSearch: () -> Unit,

    ) {
    BackHandler(isSearchFilterVisible) {
        onBackPress()
    }

    val query = searchViewModel.searchText

    Box (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))) {
        TextField(
            value = query,
            textStyle = TextStyle(
                fontFamily = ShurjoFamily,
                color = Color.Gray,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold

            ),
            onValueChange = {
                searchViewModel.searchText = it
                if (!isSearchFilterVisible)
                    onBackPress()
                else if (searchViewModel.searchText.isEmpty())
                    onBackPress()
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
            .size(32.dp)
            .background(color = PaloBlue, shape = RoundedCornerShape(3.dp)),
            contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.clickable { onSearch() }
            )
        }
    }
}

