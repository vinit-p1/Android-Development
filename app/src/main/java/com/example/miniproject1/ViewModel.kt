package com.example.miniproject1

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ViewModel(private val app: Application) : AndroidViewModel(app) {
    private val Context.dataStore by preferencesDataStore("settings")

    val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_key")
    val DYNAMIC_COLOR_KEY = booleanPreferencesKey("dynamic_color_key")

//    val userFlow: Flow<String> = app.dataStore.data.map {
//        it[userKey] ?: ""
//    }
    val isDarkTheme: Flow<Boolean> = app.dataStore.data
        .map { it[DARK_THEME_KEY] ?: false // Provide a default value
        }

    val isDynamicColor: Flow<Boolean> = app.dataStore.data
        .map { it[DYNAMIC_COLOR_KEY] ?: true // Provide a default value
        }

    fun saveOptionsPreferences(key: Preferences.Key<Boolean>, value: Boolean){
        viewModelScope.launch {
            app.dataStore.edit {preferences ->
                preferences[key] = value
            }
        }
    }
}