package com.example.runmaster.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("AppTheme")
        val USERNAME_KEY = stringPreferencesKey("userName")
        val PASSWORD_KEY = stringPreferencesKey("password")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("isLoggedIn")
    }

    val userLoginFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: ""
    }

    val userPasswordFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PASSWORD_KEY] ?: ""
    }

    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY] ?: false
    }

    suspend fun saveUserCredentials(login: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = login
            preferences[PASSWORD_KEY] = password
        }
    }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }

}
