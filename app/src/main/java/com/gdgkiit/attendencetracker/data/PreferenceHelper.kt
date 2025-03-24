package com.gdgkiit.attendencetracker.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUserData(name: String, bio: String) {
        sharedPreferences.edit().apply {
            putString("user_name", name)
            putString("user_bio", bio)
            apply()
        }
    }

    fun getUserName(): String {
        return sharedPreferences.getString("user_name", "Your Name") ?: "Your Name"
    }

    fun getUserBio(): String {
        return sharedPreferences.getString("user_bio", "Edit your bio.") ?: "Edit your bio."
    }
}