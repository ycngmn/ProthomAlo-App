package com.ycngmn.prothomalo.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily
import com.ycngmn.prothomalo.ui.assets.AppFont
import com.ycngmn.prothomalo.ui.assets.Strings
import com.ycngmn.prothomalo.ui.theme.PaloBlue

enum class FieldType {
    Text, DatePicker, MultiChoice
}

@Composable
fun FilterBar(searchViewModel: SearchViewModel) {

    val selectedDate = searchViewModel.selectedDate
    val selectedAuthor = searchViewModel.selectedAuthor
    val sectionMap = PaloGlobal.getPalo().menuMap

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {

        Column (modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(
                text = Strings.get("filter_hint"),
                color = MaterialTheme.colorScheme.onBackground,
                style = AppFont.titleTS,
                textAlign = TextAlign.Center
            )

            CustomField(
                selectedDate,
                Strings.get("label_date"),
                Icons.Default.DateRange,
                FieldType.DatePicker,
                onDateSelected = { searchViewModel.selectedDate = it }
            )
            CustomField(
                selectedAuthor,
                Strings.get("label_author"),
                Icons.Default.Person,
                FieldType.Text,
                onValueChange = { searchViewModel.selectedAuthor = it }
            )

            val sectionOptions = sectionMap.keys.map { it.first }.dropLast(1)
            CustomField(
                "",
                Strings.get("label_category"),
                Icons.Default.ArrowDropDown,
                FieldType.MultiChoice,
                options = sectionOptions,
                selectedItems = searchViewModel.selectedSections,
                onSelectionChange = { searchViewModel.selectedSections = it.toList() })
            val typeOptions =  mapOf(
                Strings.get("type_text") to "text",
                Strings.get("type_photo") to "photo",
                Strings.get("type_video") to "video",
                Strings.get("type_live_blog") to "live-blog",
                Strings.get("type_interview") to "interview"
            )
            var selectedTypes by remember { mutableStateOf(emptyList<String>()) }
            CustomField(
                "",
                Strings.get("label_type"),
                Icons.Default.ArrowDropDown,
                FieldType.MultiChoice,
                options = typeOptions.keys.toList(),
                selectedItems = selectedTypes,
                onSelectionChange = {
                    selectedTypes = it
                    searchViewModel.selectedTypes = it.map { item -> typeOptions[item].toString() }
                })
        }
    }
}


@Composable
fun CustomField(
    value: String = "",
    label: String,
    trailIconName: ImageVector,
    fieldType: FieldType,
    options: List<String> = emptyList(),
    selectedItems: List<String> = emptyList(),
    onValueChange: (String) -> Unit = {},
    onSelectionChange: (List<String>) -> Unit = {},
    onDateSelected: (String) -> Unit = {} //TODO
) {
    var isFocused by remember { mutableStateOf(false) }
    val showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val selectedText = selectedItems.joinToString(", ")

    // used Box to align dropdown menu to it's text field.
    Box {
        OutlinedTextField(
            value = if (fieldType == FieldType.MultiChoice) selectedText else value,
            onValueChange = if (fieldType == FieldType.Text) {
                onValueChange
            } else {
                {}
            },
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = trailIconName,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable {
                        when (fieldType) {
                            // FieldType.DatePicker -> showDatePicker = true
                            FieldType.MultiChoice -> expanded = !expanded
                            else -> {}
                        }
                    }
                )
            },
            label = {
                Text(
                    text = label,
                    fontFamily = ShurjoFamily,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = if (isFocused) 14.sp else 16.sp,
                    fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal
                )
            },
            readOnly = fieldType != FieldType.Text,
            enabled = fieldType != FieldType.DatePicker,
            modifier = Modifier
                .fillMaxWidth().onFocusChanged { isFocused = it.isFocused },

            interactionSource = remember { MutableInteractionSource() } // workaround to fix/add onClick
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) { // works like onClick
                                if (fieldType == FieldType.MultiChoice) expanded = !expanded
                            }
                        }
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PaloBlue,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            )
        )

        if (showDatePicker && fieldType == FieldType.DatePicker) {
            TODO()
        }

        if (expanded && fieldType == FieldType.MultiChoice) {
            val verticalState = rememberScrollState(0)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp),
                scrollState = verticalState
            ) {
                options.forEach { option ->
                    val isSelected = selectedItems.contains(option)
                    DropdownMenuItem(
                        text = {
                            Text(
                                option,
                                color = if (isSelected) PaloBlue else MaterialTheme.colorScheme.onBackground,
                                style = AppFont.dateTS2.copy(color = Color.Unspecified),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        },
                        onClick = {
                            val newSelection =
                                if (isSelected) selectedItems - option else selectedItems + option
                            onSelectionChange(newSelection)
                        }
                    )
                }
            }
        }
    }
}