package com.djumabaevs.routes

import com.djumabaevs.data.requests.CreateCommentRequest
import com.djumabaevs.data.requests.DeleteCommentRequest
import com.djumabaevs.data.responses.BasicApiResponse
import com.djumabaevs.services.ActivityService
import com.djumabaevs.services.CommentService
import com.djumabaevs.services.LikeService
import com.djumabaevs.services.UserService
import com.djumabaevs.util.ApiResponseMessages
import com.djumabaevs.util.QueryParams
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.createComment(
    commentService: CommentService,
    activityService: ActivityService
) {
    authenticate {
        post("/api/comment/create") {
            val request = call.receiveOrNull<CreateCommentRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userId = call.userId
            when(commentService.createComment(request, userId)) {
                is CommentService.ValidationEvent.ErrorFieldEmpty -> {
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = false,
                            message = ApiResponseMessages.FIELDS_BLANK
                        )
                    )
                }
                is CommentService.ValidationEvent.ErrorCommentTooLong -> {
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = false,
                            message = ApiResponseMessages.COMMENT_TOO_LONG
                        )
                    )
                }
                is CommentService.ValidationEvent.Success -> {
                    activityService.addCommentActivity(
                        byUserId = userId,
                        postId = request.postId,
                    )
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = true,
                        )
                    )
                }
            }
        }
    }
}

fun Route.getCommentsForPost(
    commentService: CommentService,
) {
    authenticate {
        get("/api/comment/get") {
            val postId = call.parameters[QueryParams.PARAM_POST_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val comments = commentService.getCommentsForPost(postId)
            call.respond(HttpStatusCode.OK, comments)
        }
    }
}

fun Route.deleteComment(
    commentService: CommentService,
    likeService: LikeService
) {
    authenticate {
        delete("/api/comment/delete") {
            val request = call.receiveOrNull<DeleteCommentRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val comment = commentService.getCommentById(request.commentId)
            if(comment?.userId != call.userId) {
                call.respond(HttpStatusCode.Unauthorized)
                return@delete
            }
            val deleted = commentService.deleteComment(request.commentId)
            if(deleted) {
                likeService.deleteLikesForParent(request.commentId)
                call.respond(HttpStatusCode.OK, BasicApiResponse(successful = true))
            } else {
                call.respond(HttpStatusCode.NotFound, BasicApiResponse(successful = false))
            }
        }
    }
}