package com.djumabaevs.routes

import com.djumabaevs.data.repository.user.UserRepository
import com.djumabaevs.data.models.User
import com.djumabaevs.data.requests.CreateAccountRequest
import com.djumabaevs.data.requests.LoginRequest
import com.djumabaevs.data.responses.BasicApiResponse
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


fun Route.createUserRoute(userRepository: UserRepository) {
    post("/api/user/create") {
        val request = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val userExists = userRepository.getUserByEmail(request.email) != null
        if (userExists) {
            call.respond(
                BasicApiResponse(
                    successful = false,
                    message = USER_ALREADY_EXISTS
                )
            )
            return@post
        }
        if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
            call.respond(
                BasicApiResponse(
                    successful = false,
                    message = FIELDS_BLANK
                )
            )
            return@post
        }
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
        call.respond(
            BasicApiResponse(successful = true)
        )
    }
}

fun Route.loginUser(userRepository: UserRepository) {
    post("/api/user/login") {
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (request.email.isBlank() || request.password.isBlank()) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val isCorrectPassword = userRepository.doesPasswordForUserMatch(
            email = request.email,
            enteredPassword = request.password
        )
        if(isCorrectPassword) {
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse(
                    successful = true
                )
            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse(
                    successful = false,
                    message = INVALID_CREDENTIALS
                )
            )
        }
    }
}