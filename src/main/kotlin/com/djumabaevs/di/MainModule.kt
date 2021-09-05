package com.djumabaevs.di

import com.djumabaevs.data.repository.follow.FollowRepository
import com.djumabaevs.data.repository.follow.FollowRepositoryImpl
import com.djumabaevs.data.repository.post.PostRepository
import com.djumabaevs.data.repository.post.PostRepositoryImpl
import com.djumabaevs.data.repository.user.UserRepository
import com.djumabaevs.data.repository.user.UserRepositoryImpl
import com.djumabaevs.util.Constants
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(Constants.DATABASE_NAME)
    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    single<FollowRepository> {
        FollowRepositoryImpl(get())
    }

    single<PostRepository> {
        PostRepositoryImpl(get())
    }
}