package com.gdgkiit.attendencetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.gdgkiit.attendencetracker.ui.theme.AttendenceTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendenceTrackerTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

