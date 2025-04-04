package com.ycngmn.prothomalo.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeAlertDialog(settingsVM: SettingsVM, onDismissRequest: () -> Unit) {

    val theme by settingsVM.theme.collectAsState()

    val openDialog = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) { openDialog.value = true }

    val options = listOf(
        stringResource(R.string.theme_auto),
        stringResource(R.string.theme_day),
        stringResource(R.string.theme_night)
    )
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
                        text = stringResource(R.string.theme),
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

                            settingsVM.toggleTheme(options.indexOf(selectedOption))
                        },
                        modifier = Modifier.align(Alignment.End).padding(start = 150.dp)
                    ) {
                        Text(
                            stringResource(R.string.confirm_theme),
                            fontSize = 16.sp,
                            fontFamily = ShurjoFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}