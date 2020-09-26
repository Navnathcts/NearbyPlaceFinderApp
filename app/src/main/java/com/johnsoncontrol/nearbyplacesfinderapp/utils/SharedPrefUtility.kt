package com.johnsoncontrol.nearbyplacesfinderapp.utils

import android.content.Context
import android.content.SharedPreferences


object SharedPrefUtility {
    val PREFS_FILE_NAME = "SettingSharedPref"
    fun getPrefManager(context: Context): SharedPreferences? {
        return context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun saveBooleanValue(context: Context, key: String, value: Boolean) {
        getPrefManager(context)?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBooleanValue(context: Context, key: String): Boolean {
        return getPrefManager(context)?.getBoolean(key, false) ?: false
    }

    fun saveStringValue(context: Context, key: String?, value: String) {
        getPrefManager(context)?.edit()?.putString(key, value)?.apply()
    }

    fun getStringValue(context: Context, key: String): String? {
        return getPrefManager(context)?.getString(key, null)
    }
}

