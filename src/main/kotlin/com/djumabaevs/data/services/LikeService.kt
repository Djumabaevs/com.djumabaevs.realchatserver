package com.djumabaevs.data.services

class LikeService(
    private val repository: LikeRepository
) {

    suspend fun likeParent(userId: String, parentId: String): Boolean {
        return repository.likeParent(userId, parentId)
    }

    suspend fun unlikeParent(userId: String, parentId: String): Boolean {
        return repository.unlikeParent(userId, parentId)
    }

    suspend fun deleteLikesForParent(parentId: String) {
        repository.deleteLikesForParent(parentId)
    }
}