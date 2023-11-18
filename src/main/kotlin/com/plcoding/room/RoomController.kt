package com.plcoding.room

import com.plcoding.data.MessageDataSource
import com.plcoding.data.model.Message
import io.ktor.http.cio.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {
    private val member = ConcurrentHashMap<String, Member>()
    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (member.containsKey(username)) {
            throw MemberAlreadyExistException()
        }
        member[username] = Member(
            username = username,
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun sendMessage(senderUsername: String, message: String) {
        member.values.forEach { member ->
            val messageEntity = Message(
                text = message,
                username = senderUsername,
                System.currentTimeMillis()
            )

            messageDataSource.insertMessage(messageEntity)

            val parseMessage = Json.encodeToString(messageEntity)
            member.socket.send(Frame.Text(parseMessage))
        }
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun tryDisconnect(username: String) {
        member[username]?.socket?.close()
        if (member.containsKey(username)) {
            member.remove(username)
        }

    }
}