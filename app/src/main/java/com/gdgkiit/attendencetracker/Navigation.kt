package com.gdgkiit.attendencetracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdgkiit.attendencetracker.screens.AboutMeScreen
import com.gdgkiit.attendencetracker.screens.SubjectScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "subjects") {
        composable("subjects") { SubjectScreen(navController) }
        composable("about") { AboutMeScreen(navController) }
    }
}