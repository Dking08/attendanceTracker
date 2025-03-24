package com.gdgkiit.attendencetracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gdgkiit.attendencetracker.model.Subject
import com.gdgkiit.attendencetracker.viewmodel.SubjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(navController: NavController, viewModel: SubjectViewModel = viewModel()) {
//    LaunchedEffect(Unit) { viewModel.fetchSubjects() }


    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance Tracker", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { navController.navigate("about") }) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+", fontSize = 24.sp)
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(viewModel.subjects) { subject ->
                SubjectCard(subject, viewModel)
            }
        }
        if (showDialog) {
            AddSubjectDialog(viewModel) { showDialog = false }
        }
    }
}

@Composable
fun SubjectCard(subject: Subject, viewModel: SubjectViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = subject.name, style = MaterialTheme.typography.titleLarge, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {

                    Text(text = "Attended: ${subject.attendance}", color = Color.LightGray)
                    Text(text = "Class Days: ${subject.classDays.joinToString()}")
                    Spacer(modifier = Modifier.height(8.dp))
//                    Button(onClick = { showDialog = true }) {
//                        Text("Update")
//                    }
               Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { viewModel.deleteSubject(subject) }) {
                    Text("Delete", color = Color.Cyan)
                }
                Button(onClick = { showDialog = true }) {
                    Text("Update")
                }
            }
                }
                Text(
                    text = "${subject.attendance}%",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Button(onClick = { showDialog = true }) {
//                Text("Update")
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
////                TextButton(onClick = { showDialog = true }) {
////                    Text("Update", color = Color.Cyan)
////                }
//                Button(onClick = { showDialog = true }) {
//                    Text("Update")
//                }
//            }
        }
    }

    if (showDialog) {
        UpdateAttendanceDialog(subject, viewModel) { showDialog = false }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddSubjectDialog(viewModel: SubjectViewModel, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var attendance by remember { mutableStateOf("") }
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val selectedDays = remember { mutableStateListOf<String>() }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add New Subject", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Subject Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = attendance,
                    onValueChange = { attendance = it },
                    label = { Text("Current Attendance %") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text("Select Class Days:", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    daysOfWeek.forEach { day ->
                        FilterChip(
                            selected = selectedDays.contains(day),
                            onClick = {
                                if (selectedDays.contains(day)) selectedDays.remove(day)
                                else selectedDays.add(day)
                            },
                            label = { Text(day) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF007AFF),
                                selectedLabelColor = Color.White,
                                disabledContainerColor = Color.LightGray
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val attendanceInt = attendance.toIntOrNull() ?: return@Button
                    viewModel.addSubject(name, attendanceInt, selectedDays)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Subject")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}



@Composable
fun UpdateAttendanceDialog(subject: Subject, viewModel: SubjectViewModel, onDismiss: () -> Unit) {
    var newAttendance by remember { mutableStateOf(subject.attendance.toString()) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Update Attendance") },
        text = {
            OutlinedTextField(
                value = newAttendance,
                onValueChange = { newAttendance = it },
                label = { Text("Enter new %") }
            )
        },
        confirmButton = {
            Button(onClick = {
                viewModel.updateAttendance(subject.name, newAttendance.toIntOrNull() ?: subject.attendance)
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
