package com.djumabaevs.routes

import com.djumabaevs.controller.user.UserController
import com.djumabaevs.data.requests.CreateAccountRequest
import com.djumabaevs.data.responses.BasicApiResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import com.djumabaevs.util.*
import com.djumabaevs.util.ApiResponseMessages.USER_ALREADY_EXISTS

fun Route.userRoutes() {
    val userController: UserController by inject()
    route("/api/user/create") {
        post {
            val request = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userExists = userController.getUserByEmail(request.email) != null
            if(userExists) {
                call.respond(
                    BasicApiResponse(
                        successful = false,
                        message = USER_ALREADY_EXISTS
                    )
                )
                return@post
            }
        }
    }
}