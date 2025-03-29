package com.ycngmn.prothomalo.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.ui.screens.home.BottomBar


@Composable
fun SettingScreen(settingsVM: SettingsVM, navController: NavController) {

    Scaffold(
        bottomBar = { BottomBar(navController)}
    ) {
        Column (modifier = Modifier.padding(it)){
            Settings(settingsVM, navController)
        }
    }
}


@Composable
fun Settings(settingsVM: SettingsVM, navController: NavController) {

    var isShowThemeDialog by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableFloatStateOf(1f) }

    var checked by remember { mutableStateOf(settingsVM.isSeeMoreEnabled.value) }

    if (isShowThemeDialog) {
        ThemeAlertDialog(settingsVM) { isShowThemeDialog = false }
    }

    val themeOptions = listOf("স্বয়ংক্রিয়", "দিবা", "নিশা")
    val themeSubtitle = themeOptions.joinToString(", ")

    val scrollState = rememberScrollState()
    Column (modifier = Modifier.verticalScroll(scrollState)){
        SettingTile(
            R.drawable.bulb_theme_24px, "প্রদর্শন শৈলী",
            themeSubtitle,
            Modifier.fillMaxWidth()
                .clickable { isShowThemeDialog = true }
        )

        SettingTile(
            R.drawable.list_alt_24px,
            "বিভাগ সজ্জা",
            "প্রচ্ছদ পাতায় বিভাগের পুনর্বিন্যাস",
            Modifier.fillMaxWidth()
        )

        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            SettingTile(
                R.drawable.custom_typography_24px,
                "অক্ষরের আকার",
                "ছোট, মাঝারি, বড়",
            )
            Slider(
                modifier = Modifier.padding(end = 16.dp),
                value = sliderValue,
                valueRange = 0f..2f,
                steps = 1,
                onValueChange = { sliderValue = it }
            )

        }

        Row (verticalAlignment = Alignment.CenterVertically) {
            SettingTile(
                R.drawable.more_horiz_24px,
                "আরও পড়ুন",
                "খবরের শেষে 'আরও পড়ুন' অংশ"
            )

            Spacer(Modifier.width(16.dp))

            Switch(
                modifier = Modifier,
                checked = checked,
                onCheckedChange = {
                    checked = it
                    settingsVM.toggleSeeMore(it)
                }
            )
        }
        val isDarkTheme = settingsVM.theme.collectAsState().value == 2
                || (settingsVM.theme.collectAsState().value == 0 && isSystemInDarkTheme())
        SubButtons(isDarkTheme, settingsVM, navController)
    }

}



