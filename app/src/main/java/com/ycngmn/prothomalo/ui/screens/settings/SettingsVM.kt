package com.ycngmn.prothomalo.ui.screens.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ycngmn.prothomalo.prothomalo.subs.PaloKeys
import com.ycngmn.prothomalo.utils.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsVM(private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _paloKey = MutableStateFlow(PaloKeys.PaloMain)
    val paloKey: StateFlow<PaloKeys> = _paloKey

    private val _theme = MutableStateFlow(0)
    val theme: StateFlow<Int> = _theme

    private val _isSeeMoreEnabled = mutableStateOf(true)
    val isSeeMoreEnabled: State<Boolean> = _isSeeMoreEnabled

    init {
        runBlocking { // wait to avoid premature theme load.
            _theme.value = dataStoreManager.themeState.first()
            _isSeeMoreEnabled.value =  dataStoreManager.seeMoreState.first()
            _paloKey.value = dataStoreManager.paloKey.first()
        }
    }

    fun setPaloKey(key: PaloKeys) {
        _paloKey.value = key
        viewModelScope.launch {
            dataStoreManager.savePaloKey(key)
        }
    }

    fun toggleTheme(state: Int) {
        _theme.value = state
        viewModelScope.launch {
            dataStoreManager.saveThemeState(state)
        }
    }

    fun toggleSeeMore(state: Boolean) {
        _isSeeMoreEnabled.value = state
        viewModelScope.launch {
            dataStoreManager.saveSeeMoreState(state)
        }
    }

}

class SettingsVMFactory(private val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsVM(dataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}