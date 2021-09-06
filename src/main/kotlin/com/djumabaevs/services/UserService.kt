package com.djumabaevs.services

import com.djumabaevs.data.repository.user.UserRepository
import com.djumabaevs.data.requests.LoginRequest

class UserService(
    private val repository: UserRepository
) {
    suspend fun doesUserWithEmailExists(email: String): Boolean {
        return repository.getUserByEmail(email) != null
    }
    suspend fun doesEmailBelongToUserId(email: String, userId: String): Boolean {
        return repository.doesEmailBelongToUserId(email, userId)
    }

    suspend fun doesPasswordMatchForUser(request: LoginRequest): Boolean {
        return repository.doesPasswordForUserMatch(
            email = request.email,
            enteredPassword = request.password
        )
    }
}