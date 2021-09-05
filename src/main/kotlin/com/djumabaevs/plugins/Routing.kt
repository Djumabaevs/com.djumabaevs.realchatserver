package com.djumabaevs.plugins

import com.djumabaevs.data.repository.follow.FollowRepository
import com.djumabaevs.data.repository.post.PostRepository
import com.djumabaevs.data.repository.user.UserRepository
import com.djumabaevs.routes.*
import com.djumabaevs.services.CommentService
import com.djumabaevs.services.FollowService
import com.djumabaevs.services.LikeService
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userService: UserService by inject()
    val followService: FollowService by inject()
    val postService: PostService by inject()
    val likeService: LikeService by inject()
    val commentService: CommentService by inject()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
    routing {
        // User routes
        createUser(userService)
        loginUser(
            userService = userService,
            jwtIssuer = jwtIssuer,
            jwtAudience = jwtAudience,
            jwtSecret = jwtSecret
        )

        // Following routes
        followUser(followService)
        unfollowUser(followService)

        // Post routes
        createPost(postService, userService)
        getPostsForFollows(postService, userService)
        deletePost(postService, userService, likeService)

        // Like routes
        likeParent(likeService, userService)
        unlikeParent(likeService, userService)

        // Comment routes
        createComment(commentService, userService)
        deleteComment(commentService, userService, likeService)
        getCommentsForPost(commentService)
    }
}
