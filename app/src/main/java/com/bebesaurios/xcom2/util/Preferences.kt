package com.bebesaurios.xcom2.util

import android.content.SharedPreferences

class Preferences(private val sharedPreferences: SharedPreferences) {
    fun getMSIToken(): String {
        return sharedPreferences.getString("msiToken", "") ?: ""
    }
}