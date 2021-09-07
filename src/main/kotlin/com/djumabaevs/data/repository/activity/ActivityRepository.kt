package com.djumabaevs.data.repository.activity

import com.djumabaevs.data.models.Activity
import com.djumabaevs.util.Constants

interface ActivityRepository {

    suspend fun getActivitiesForUser(
        userId: String,
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_ACTIVITY_PAGE_SIZE
    ): List<Activity>

    suspend fun createActivity(activity: Activity)

    suspend fun deleteActivity(activityId: String): Boolean
}