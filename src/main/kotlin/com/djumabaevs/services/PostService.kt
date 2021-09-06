package com.djumabaevs.services

import com.djumabaevs.data.models.Post
import com.djumabaevs.data.repository.post.PostRepository
import com.djumabaevs.data.requests.CreatePostRequest

class PostService(
    private val repository: PostRepository
) {
    suspend fun createPostIfUserExists(request: CreatePostRequest): Boolean {
        return repository.createPostIfUserExists(
            Post(
                imageUrl = "",
                userId = request.userId,
                timestamp = System.currentTimeMillis(),
                description = request.description
            )
        )
    }
    suspend fun getPostForFollows
}