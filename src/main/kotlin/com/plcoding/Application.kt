package com.plcoding

import com.plcoding.data.di.mainModule
import com.plcoding.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(org.koin.ktor.ext.Koin) {
        modules(com.plcoding.data.di.mainModule)
    }
    configureSockets()
    configureRouting()

    configureSecurity()
    configureSerialization()
    configureMonitoring()
}
