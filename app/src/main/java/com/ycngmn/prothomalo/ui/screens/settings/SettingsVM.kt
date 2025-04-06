package com.ycngmn.prothomalo.ui.screens.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ycngmn.prothomalo.prothomalo.subs.PaloKeys
import com.ycngmn.prothomalo.ui.assets.AppFontSize
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

    private val _appFontSize = mutableStateOf(AppFontSize.MEDIUM)
    val appFontSize: State<AppFontSize> = _appFontSize

    private val _articleTextSize = mutableIntStateOf(0)
    val articleTextSize: State<Int> = _articleTextSize

    init {
        runBlocking { // wait to avoid premature loadings.
            _theme.value = dataStoreManager.themeState.first()
            _isSeeMoreEnabled.value =  dataStoreManager.seeMoreState.first()
            _paloKey.value = dataStoreManager.paloKey.first()
            _articleTextSize.intValue = dataStoreManager.articleTextSize.first()
            _appFontSize.value = dataStoreManager.appFontSize.first()

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

    fun setAppFontSize(key: AppFontSize) {
        _appFontSize.value = key
        viewModelScope.launch {
            dataStoreManager.saveAppFontSize(key)
        }
    }

    fun setArticleTextSize(state: Int) {
        _articleTextSize.intValue = state
        viewModelScope.launch {
            dataStoreManager.saveArticleTextSize(state)
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