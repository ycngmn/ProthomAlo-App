package com.ycngmn.prothomalo.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager (private val context: Context) {
    private val themeStateKey = intPreferencesKey("theme_state")

    val themeState : Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[themeStateKey] ?: 0
    }

    suspend fun saveThemeState(state: Int) {
        context.dataStore.edit { preferences ->
            preferences[themeStateKey] = state
        }

    }
}