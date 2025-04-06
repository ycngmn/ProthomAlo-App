package com.ycngmn.prothomalo.ui.screens.settings

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.ui.assets.AppFontSize
import com.ycngmn.prothomalo.ui.assets.Strings
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
    var sliderValue by remember { mutableFloatStateOf(settingsVM.appFontSize.value.ordinal.toFloat()) }

    var checked by remember { mutableStateOf(settingsVM.isSeeMoreEnabled.value) }
    val context = LocalContext.current

    if (isShowThemeDialog) {
        ThemeAlertDialog(settingsVM) { isShowThemeDialog = false }
    }

    val scrollState = rememberScrollState()
    Column (modifier = Modifier.verticalScroll(scrollState)){
        SettingTile(
            R.drawable.bulb_theme_24px,
            Strings.get("theme"),
            Strings.get("theme_hint"),
            Modifier.fillMaxWidth()
                .clickable { isShowThemeDialog = true }
        )

        SettingTile(
            R.drawable.list_alt_24px,
            Strings.get("section_rearrange"),
            Strings.get("section_rearrange_hint"),
            Modifier.fillMaxWidth()
                .clickable { Toast.makeText(context, "Not yet implemented", Toast.LENGTH_SHORT).show() }
        )

        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            SettingTile(
                R.drawable.custom_typography_24px,
                Strings.get("font_size"),
                Strings.get("font_size_hint"),
            )
            Slider(
                modifier = Modifier.padding(end = 16.dp),
                value = sliderValue,
                valueRange = 0f..2f,
                steps = 1,
                onValueChange = {
                    settingsVM.setAppFontSize(
                        when (it.toInt()) {
                            0 -> AppFontSize.SMALL
                            1 -> AppFontSize.MEDIUM
                            else -> AppFontSize.LARGE
                        }
                    )
                    sliderValue = it
                }
            )

        }

        Row (verticalAlignment = Alignment.CenterVertically) {
            SettingTile(
                R.drawable.more_horiz_24px,
                Strings.get("read_more"),
                Strings.get("read_more_hint")
            )

            Spacer(Modifier.weight(1F))

            Switch(
                modifier = Modifier.padding(end = 16.dp),
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



