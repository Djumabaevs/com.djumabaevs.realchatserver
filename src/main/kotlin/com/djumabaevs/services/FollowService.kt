package com.djumabaevs.services

import com.djumabaevs.data.repository.follow.FollowRepository
import com.djumabaevs.data.requests.FollowUpdateRequest

class FollowService(
    private val followRepository: FollowRepository
) {

    suspend fun followUserIfExists(request: FollowUpdateRequest): Boolean {
        return followRepository.followUserIfExists(
            request.followingUserId,
            request.followedUserId
        )
    }

    suspend fun unfollowUserIfExists(request: FollowUpdateRequest): Boolean {
        return followRepository.unfollowUserIfExists(
            request.followingUserId,
            request.followedUserId
        )
    }
}