package com.plcoding.data.di

import com.plcoding.data.MessageDataSource
import com.plcoding.data.MessageDataSourceImp
import com.plcoding.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine

val mainModule = module {
    single {
        org.litote.kmongo.reactivestreams.KMongo.createClient().coroutine.getDatabase("message_db_yt")
    }
    single<MessageDataSource> {
        MessageDataSourceImp(get())
    }
    single { RoomController(get()) }
}