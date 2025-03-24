package com.gdgkiit.attendencetracker.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.gdgkiit.attendencetracker.data.PreferenceHelper

class AboutMeViewModel(application: Application) : AndroidViewModel(application) {
    private val preferenceHelper = PreferenceHelper(application)

    var userName = mutableStateOf(preferenceHelper.getUserName())
    var userBio = mutableStateOf(preferenceHelper.getUserBio())

    fun saveProfile() {
        preferenceHelper.saveUserData(userName.value, userBio.value)
    }
}