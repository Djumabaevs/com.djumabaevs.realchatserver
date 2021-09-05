package com.djumabaevs.plugins

import com.djumabaevs.data.repository.follow.FollowRepository
import com.djumabaevs.data.repository.post.PostRepository
import com.djumabaevs.data.repository.user.UserRepository
import com.djumabaevs.routes.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository: UserRepository by inject()
    val followRepository: FollowRepository by inject()
    val postRepository: PostRepository by inject()

    routing {
        // User routes
        createUserRoute(userRepository)
        loginUser(userRepository)

        // Following routes
        followUser(followRepository)
        unfollowUser(followRepository)

        // Post routes
        createPostRoute(postRepository)
    }
}
