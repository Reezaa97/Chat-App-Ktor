package com.plcoding.plugins

import com.plcoding.room.RoomController
import com.plcoding.rout.chatSocket
import com.plcoding.rout.getAllMessages
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Application.configureRouting() {
    val roomController by inject<RoomController>()
    install(Routing) {
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}
