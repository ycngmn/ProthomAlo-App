package com.ycngmn.prothomalo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.utils.ThemeViewModel


@Composable
fun MenuScreen(themeViewModel: ThemeViewModel, navController: NavHostController) {

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    Scaffold (
        topBar = { MenuTopBar() },
        bottomBar = { BottomBar(navController) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dark Mode")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { themeViewModel.toggleTheme() }
            )
        }

    }

}

@Composable
fun MenuTopBar() {
    Surface (
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        shadowElevation = 4.dp
    ) {
        Box (modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.Center).padding(horizontal = 40.dp),
                text = "Menu",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ShurjoFamily,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}