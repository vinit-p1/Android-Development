package com.example.miniproject1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class DataStorePreferences(val context: Context) {

    private object PreferencesKeys{
        val isDark: Preferences.Key<Boolean> = booleanPreferencesKey("DARK_THEME")
        val isDynamic: Preferences.Key<Boolean> = booleanPreferencesKey("DYNAMIC_THEME")
    }

    suspend fun updatePref(key: Preferences.Key<Boolean>, value: Boolean) = context.dataStore.edit{
        preferences: MutablePreferences ->
        preferences[key] = value
    }

    suspend fun getPref(key: Preferences.Key<Boolean>): Flow<Boolean> = context.dataStore.data.map {
        preferences: Preferences ->
        return@map preferences[key] ?: false
    }
}