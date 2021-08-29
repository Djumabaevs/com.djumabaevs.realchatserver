package com.djumabaevs

import com.djumabaevs.di.mainModule
import io.ktor.application.*
import com.djumabaevs.plugins.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.modules

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSecurity()
    configureRouting()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
//    configureSockets()
    install(Koin) {
        modules(mainModule)
    }

}

