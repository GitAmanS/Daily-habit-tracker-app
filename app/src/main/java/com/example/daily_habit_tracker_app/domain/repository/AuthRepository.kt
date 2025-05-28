package com.example.daily_habit_tracker_app.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signup(email: String, password: String): Result<Unit>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
}

