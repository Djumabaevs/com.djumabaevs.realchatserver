package com.djumabaevs.di

import com.djumabaevs.data.repository.comment.CommentRepository
import com.djumabaevs.data.repository.comment.CommentRepositoryImpl
import com.djumabaevs.data.repository.follow.FollowRepository
import com.djumabaevs.data.repository.follow.FollowRepositoryImpl
import com.djumabaevs.data.repository.likes.LikeRepository
import com.djumabaevs.data.repository.likes.LikeRepositoryImpl
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
    single<LikeRepository> {
        LikeRepositoryImpl(get())
    }
    single<CommentRepository> {
        CommentRepositoryImpl(get())
    }
    single { UserService(get()) }
    single { FollowService(get()) }
    single { PostService(get()) }
    single { LikeService(get()) }
    single { CommentService(get()) }
}