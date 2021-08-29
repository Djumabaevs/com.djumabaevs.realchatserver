package com.djumabaevs.controller.user

import com.djumabaevs.data.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase

class UserControllerImpl(
    db: CoroutineDatabase
): UserController {

    private val users = db.getCollection<User>()

    override suspend fun createUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByEmail(email: String): User? {
        TODO("Not yet implemented")
    }

}