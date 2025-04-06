package com.ycngmn.prothomalo.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.ui.assets.Strings
import com.ycngmn.prothomalo.ui.assets.AppFont
import com.ycngmn.prothomalo.utils.selectColorByIndex

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)

@Composable
fun BottomBar(navController: NavController) {

    val sections = listOf(
        BottomNavItem("home", R.drawable.pa_icon,Strings.get("home_tab")),
        BottomNavItem("Settings", R.drawable.discover_icon, Strings.get("settings_tab")),
        BottomNavItem("Bookmark", R.drawable.bookmark_icon, Strings.get("bookmark_tab")),
        BottomNavItem("Menu", R.drawable.menu_icon, Strings.get("menu_tab"))
    )

    sections.map { it.route }

    val currentRoute = navController.currentDestination?.route

    Surface (modifier = Modifier.fillMaxWidth(), shadowElevation = 10.dp) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.shadow(50.dp).height(60.dp)
        ) {
            sections.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Column (horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painterResource(item.icon),
                                contentDescription = item.label,
                                modifier = Modifier.size(27.dp),
                            )
                            Text(text = item.label, style = AppFont.navBottomTS)
                        }
                    },
                    selected = currentRoute == item.route,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectColorByIndex(index),
                        selectedTextColor = selectColorByIndex(index),
                        indicatorColor = MaterialTheme.colorScheme.background,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                    ),
                    onClick = {

                        if (!navController.popBackStack(route=item.route, inclusive = false)) {
                            navController.navigate(item.route) {
                                popUpTo(item.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }


        }
    }
}