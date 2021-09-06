package com.djumabaevs.services

import com.djumabaevs.data.repository.user.UserRepository

class UserService(
    private val repository: UserRepository
) {
    suspend fun doesUserWithEmailExists(email: String): Boolean {
        return repository.getUserByEmail(email) != null
    }
}