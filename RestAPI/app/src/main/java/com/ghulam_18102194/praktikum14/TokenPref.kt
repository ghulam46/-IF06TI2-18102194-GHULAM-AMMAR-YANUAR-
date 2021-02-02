package com.ghulam_18102194.praktikum14

import android.content.Context
import com.ghulam_18102194.praktikum14.model.Token

internal class TokenPref(context: Context) {
    companion object {
        private const val PREFS_NAME = "token_pref"
        private const val TOKEN = "token"
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME,
        Context.MODE_PRIVATE)
    fun setToken(value: Token) {
        val editor = preferences.edit()
        editor.putString(TOKEN, value.token)
        editor.apply()
    }
    fun getToken(): Token {
        val model = Token()
        model.token = preferences.getString(TOKEN, "")
        return model
    }
    fun removeToken() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}