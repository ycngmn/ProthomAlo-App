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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily
import com.ycngmn.prothomalo.utils.selectColorByIndex

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)

@Composable
fun BottomBar(navController: NavController) {

    val sections = listOf(
        BottomNavItem("home", R.drawable.pa_icon, "প্রচ্ছদ"),
        BottomNavItem("Settings", R.drawable.discover_icon, "বিন্যাস"),
        BottomNavItem("Bookmark", R.drawable.bookmark_icon, "সংরক্ষণ"),
        BottomNavItem("Menu", R.drawable.menu_icon, "তালিকা")
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
                            Text(
                                item.label, fontSize = 11.sp,
                                fontFamily = ShurjoFamily, fontWeight = FontWeight.Bold
                            )
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