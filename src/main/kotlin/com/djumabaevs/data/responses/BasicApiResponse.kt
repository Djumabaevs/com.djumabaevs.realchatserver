package com.djumabaevs.data.responses

data class BasicApiResponse(
    val successful: Boolean,
    val message: String? = null
)