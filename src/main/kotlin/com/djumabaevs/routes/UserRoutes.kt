package com.djumabaevs.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.djumabaevs.data.repository.user.UserRepository
import com.djumabaevs.data.models.User
import com.djumabaevs.data.repository.follow.FollowRepository
import com.djumabaevs.data.requests.CreateAccountRequest
import com.djumabaevs.data.requests.LoginRequest
import com.djumabaevs.data.responses.AuthResponse
import com.djumabaevs.data.responses.BasicApiResponse
import com.djumabaevs.data.responses.UserResponseItem
import com.djumabaevs.services.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import com.djumabaevs.util.*
import com.djumabaevs.util.ApiResponseMessages.FIELDS_BLANK
import com.djumabaevs.util.ApiResponseMessages.INVALID_CREDENTIALS
import com.djumabaevs.util.ApiResponseMessages.USER_ALREADY_EXISTS
import io.ktor.auth.*
import java.util.*


class UserService(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) {

    suspend fun doesUserWithEmailExist(email: String): Boolean {
        return userRepository.getUserByEmail(email) != null
    }

    suspend fun doesEmailBelongToUserId(email: String, userId: String): Boolean {
        return userRepository.doesEmailBelongToUserId(email, userId)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

    fun isValidPassword(enteredPassword: String, actualPassword: String): Boolean {
        return enteredPassword == actualPassword
    }

    suspend fun searchForUsers(query: String, userId: String): List<UserResponseItem> {
        val users = userRepository.searchForUsers(query)
        val followsByUser = followRepository.getFollowsByUser(userId)
        return users.map { user ->
            val isFollowing = followsByUser.find { it.followedUserId == user.id } != null
            UserResponseItem(
                username = user.username,
                profilePictureUrl = user.profileImageUrl,
                bio = user.bio,
                isFollowing = isFollowing
            )
        }
    }

    suspend fun createUser(request: CreateAccountRequest) {
        userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                profileImageUrl = "",
                bio = "",
                gitHubUrl = null,
                instagramUrl = null,
                linkedInUrl = null
            )
        )
    }

    fun validateCreateAccountRequest(request: CreateAccountRequest): UserService.ValidationEvent {
        if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
            return ValidationEvent.ErrorFieldEmpty
        }
        return ValidationEvent.Success
    }

    sealed class ValidationEvent {
        object ErrorFieldEmpty : ValidationEvent()
        object Success : ValidationEvent()
    }
}