package com.ycngmn.prothomalo.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.screens.home.BottomBar
import com.ycngmn.prothomalo.utils.ThemeViewModel
import com.ycngmn.prothomalo.utils.restartApp


@Composable
fun LanguageButton(imgId: Int, onClick: () -> Unit) {
    Button(
        modifier = Modifier.height(62.dp).width(160.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, Color.Gray),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick() }
    ) {
        Image(
            painter = painterResource(id = imgId),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LanguageButtons(isDarkTheme: Boolean, themeViewModel: ThemeViewModel) {
    val context = LocalContext.current

    FlowRow (modifier = Modifier.padding(16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        LanguageButton(
            if (isDarkTheme ) R.drawable.palo_bangla_night
            else R.drawable.palo_bangla_logo) {
            themeViewModel.setPaloKey("PaloMain")
            restartApp(context)
        }
        LanguageButton(
            if (isDarkTheme) R.drawable.palo_eng_night
            else R.drawable.english_site_og_image
        ) {
            themeViewModel.setPaloKey("PaloEnglish")
            restartApp(context)
        }
        LanguageButton(
            if (isDarkTheme) R.drawable.kishore_alo_night
            else R.drawable.kishore_alo_logo) { }
        LanguageButton(R.drawable.muktijuddho_logo) { }
        LanguageButton(R.drawable.biggan_chinta_logo) { }
        LanguageButton(R.drawable.nagorik_songbad_logo) { }
        LanguageButton(
            if (isDarkTheme) R.drawable.bondhushava_night
            else R.drawable.bondhushava_logo) { }
        LanguageButton(
            if (isDarkTheme) R.drawable.palo_trust_night
            else R.drawable.prothomalo_trust_logo) { }


    }

}


@Composable
fun ProfileScreen(themeViewModel: ThemeViewModel, navController: NavController) {


    Scaffold(
        topBar = { ProfileTopBar() },
        bottomBar = { BottomBar(navController)}
    ) {
        Column (modifier = Modifier.padding(it)){
            ProfileScreenPreview(themeViewModel)
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicAlertDialogSample(themeViewModel: ThemeViewModel, onDismissRequest: () -> Unit) {

    val theme by themeViewModel.theme.collectAsState()

    val openDialog = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        openDialog.value = true
    }

    val options = listOf("স্বয়ংক্রিয়", "উজ্জ্বল", "অন্ধকার")
    var selectedOption by remember { mutableStateOf(options[theme]) } // Default selection


    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                onDismissRequest()
                openDialog.value = false
            }
        ) {
            Surface(
                modifier = Modifier.wrapContentHeight().wrapContentWidth(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp).selectableGroup()) {
                    Text(
                        text = "প্রদর্শন শৈলী",
                        modifier = Modifier.padding(bottom = 16.dp),
                        fontSize = 22.sp,
                        fontFamily = ShurjoFamily,
                        fontWeight = FontWeight.Bold
                    )

                    options.forEach { option ->
                        Row (
                            modifier = Modifier.fillMaxWidth()
                                .clickable { selectedOption = option },
                            verticalAlignment = Alignment.CenterVertically) {

                            RadioButton(
                                selected = option == selectedOption,
                                modifier = Modifier.scale(0.8f),
                                onClick = { selectedOption = option  }
                            )

                            Text(
                                text = option,
                                fontSize = 18.sp,
                                fontFamily = ShurjoFamily,
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onDismissRequest()

                            themeViewModel.toggleTheme(options.indexOf(selectedOption))
                                  },
                        modifier = Modifier.align(Alignment.End).padding(start = 150.dp)
                    ) {
                        Text("নিশ্চিত করুন",
                            fontSize = 16.sp,
                            fontFamily = ShurjoFamily,
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}


@Composable
fun SettingTile(iconRes: Int, title: String, subtitle: String, modifier: Modifier = Modifier ) {

    Row(
        modifier = modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painterResource(id = iconRes), contentDescription = title,
            modifier = Modifier.padding(end = 30.dp).size(30.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Column {
            Text(
                text = title,
                fontSize = 20.sp,
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = subtitle,
                fontSize = 15.sp,
                fontFamily = ShurjoFamily,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ProfileScreenPreview(themeViewModel: ThemeViewModel) {

    var isShowThemeDialog by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableFloatStateOf(1f) }

    var checked by remember { mutableStateOf(themeViewModel.isSeeMoreEnabled.value) }

    if (isShowThemeDialog) {
        BasicAlertDialogSample(themeViewModel) { isShowThemeDialog = false }
    }

    val scrollState = rememberScrollState()
    Column (modifier = Modifier.verticalScroll(scrollState)){
        SettingTile(
            R.drawable.bulb_theme_24px, "প্রদর্শন শৈলী",
            "অন্ধকার, উজ্জ্বল বা স্বয়ংক্রিয়",
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
                    themeViewModel.toggleSeeMore(it)
                }
            )
        }
        val isDarkTheme = themeViewModel.theme.collectAsState().value == 2
                || (themeViewModel.theme.collectAsState().value == 0 && isSystemInDarkTheme())
        LanguageButtons(isDarkTheme, themeViewModel)
    }

}


@Composable
fun ProfileTopBar() {
    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        shadowElevation = 4.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 40.dp),
                text = "Settings",
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
