package com.djumabaevs.routes

import com.djumabaevs.controller.user.UserController
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userController: UserController by inject()
}