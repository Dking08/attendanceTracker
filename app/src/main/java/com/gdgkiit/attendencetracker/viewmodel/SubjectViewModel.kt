package com.gdgkiit.attendencetracker.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gdgkiit.attendencetracker.model.Subject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class SubjectViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("AttendanceData", Context.MODE_PRIVATE)
    private val gson = Gson()

    var subjects = mutableStateListOf<Subject>()
        private set

    init {
        loadSubjects()
        updateSubjectsFromAPI()
    }

    fun addSubject(name: String, attendance: Int, classDays: List<String>) {
        viewModelScope.launch {
            val response = fetchAttendanceData(attendance, classDays)
            subjects.add(Subject(name, response.updatedAttendance, classDays, response.possibleLeaves))
            saveSubjects()
        }
    }

    data class AttendanceResponse(val updatedAttendance: Int, val possibleLeaves: Int, val attPercentage: Int)

    private suspend fun fetchAttendanceData(attendance: Int, classDays: List<String>): AttendanceResponse {
        val url = "https://example.com/api/attendance?attendance=$attendance&days=${classDays.joinToString(",")}"

        return withContext(Dispatchers.IO) {
            try {
//                val response = URL(url).readText()
//                val json = JSONObject(response)
                AttendanceResponse(
//                    updatedAttendance = json.getInt("updatedAttendance"),
//                    possibleLeaves = json.getInt("possibleLeaves")
                    updatedAttendance = 82,
                    possibleLeaves = 10,
                    attPercentage = 88
                )
            } catch (e: Exception) {
                AttendanceResponse(attendance, 0, 0) // Default if API fails
            }
        }
    }

    private fun updateSubjectWithAPI(subject: Subject) {
        viewModelScope.launch {
            val response = fetchAttendanceData(subject.attendance, subject.classDays)
            val updatedSubject = subject.copy(
                attendance = response.updatedAttendance,
                possibleLeaves = response.possibleLeaves
            )
            val index = subjects.indexOf(subject)
            if (index != -1) subjects[index] = updatedSubject
            saveSubjects()
        }
    }

    private fun updateSubjectsFromAPI() {
        viewModelScope.launch {
            subjects.forEach { subject ->
                updateSubjectWithAPI(subject)
            }
        }
    }

    fun updateAttendance(subjectName: String, newAttendance: Int) {
        val index = subjects.indexOfFirst { it.name == subjectName }
        if (index != -1) {
            subjects[index] = subjects[index].copy(attendance = newAttendance)
            saveSubjects()
        }
    }

    fun deleteSubject(subject: Subject) {
        subjects.remove(subject)
        saveSubjects()
    }

    private fun saveSubjects() {
        val json = gson.toJson(subjects)
        sharedPreferences.edit().putString("subjects", json).apply()
    }

    private fun loadSubjects() {
        val json = sharedPreferences.getString("subjects", null)
        if (json != null) {
            val type = object : TypeToken<List<Subject>>() {}.type
            subjects.addAll(gson.fromJson(json, type))
        }
    }
}