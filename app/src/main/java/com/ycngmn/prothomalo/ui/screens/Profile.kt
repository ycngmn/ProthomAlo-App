package com.ycngmn.prothomalo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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

    val options = listOf("সিস্টেম", "উজ্জ্বল", "অন্ধকার")
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
fun ProfileScreenPreview(themeViewModel: ThemeViewModel) {

    var isShowThemeDialog by remember { mutableStateOf(false) }

    if (isShowThemeDialog) {
        BasicAlertDialogSample(themeViewModel) {
            isShowThemeDialog = false
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth().clickable { isShowThemeDialog = true }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(id = R.drawable.routine_24px), contentDescription = "Dark Mode",
            modifier = Modifier.padding(end = 30.dp).size(30.dp)
        )
        Column {
            Text(
                text = "প্রদর্শন শৈলী",
                fontSize = 20.sp,
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "অন্ধকার, উজ্জ্বল বা সিস্টেম",
                fontSize = 18.sp,
                fontFamily = ShurjoFamily,
                color = Color.DarkGray
            )
        }
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
