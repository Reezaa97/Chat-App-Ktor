package com.plcoding.plugins

import com.plcoding.session.ChatSession
import io.ktor.application.*
import io.ktor.sessions.*
import io.ktor.util.*


fun Application.configureSecurity() {

    install(Sessions) {

        cookie<ChatSession>("MY_SESSION")
    }
    intercept(ApplicationCallPipeline.Features) {
        if (call.sessions.get<ChatSession>() == null) {
            val username = call.parameters["username"] ?: "Guest"
            call.sessions.set(ChatSession(username, generateNonce()))
        }
    }
}

