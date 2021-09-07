package com.djumabaevs.routes

import com.djumabaevs.plugins.userId
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

val ApplicationCall.userId: String
    get() = principal<JWTPrincipal>()?.userId.toString()


//suspend fun PipelineContext<Unit, ApplicationCall>.ifEmailBelongsToUser(
//    userId: String,
//    validateEmail: suspend (email: String, userId: String) -> Boolean,
//    onSuccess: suspend () -> Unit
//) {
//    val isEmailByUser = validateEmail(
//        call.principal<JWTPrincipal>()?.email ?: "",
//        userId
//    )
//    if(isEmailByUser) {
//        onSuccess()
//    } else {
//        call.respond(HttpStatusCode.Unauthorized)
//    }
//}