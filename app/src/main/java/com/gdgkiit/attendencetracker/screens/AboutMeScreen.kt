package com.gdgkiit.attendencetracker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gdgkiit.attendencetracker.viewmodel.AboutMeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeScreen(navController: NavController, viewModel: AboutMeViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Profile") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = viewModel.userName.value, fontSize = 32.sp, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = viewModel.userBio.value, fontSize = 18.sp)
        }
    }
}