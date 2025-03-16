package com.ycngmn.prothomalo.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class SearchViewModel : ViewModel() {

    var searchText by mutableStateOf("")
    var selectedDate by mutableStateOf("")
    var selectedAuthor by  mutableStateOf("")
    var selectedSections by  mutableStateOf(emptyList<String>())
    var selectedTypes by mutableStateOf(emptyList<String>())

}