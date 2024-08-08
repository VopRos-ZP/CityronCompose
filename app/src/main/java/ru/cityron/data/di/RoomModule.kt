package ru.cityron.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.cityron.data.local.EventsStore
import ru.cityron.data.room.controller.ControllerDatabase
import ru.cityron.data.room.event.EventDatabase
import ru.cityron.data.room.ip.IpDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideControllerDatabase(
        @ApplicationContext context: Context
    ): ControllerDatabase = Room.databaseBuilder(
        context = context,
        klass = ControllerDatabase::class.java,
        name = "controllers"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideIpDatabase(
        @ApplicationContext context: Context
    ): IpDatabase = Room.databaseBuilder(
        context = context,
        klass = IpDatabase::class.java,
        name = "ip"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideEventDatabase(
        @ApplicationContext context: Context
    ): EventDatabase = Room.databaseBuilder(
        context = context,
        klass = EventDatabase::class.java,
        name = "event"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideEventsStore(
        @ApplicationContext context: Context
    ): EventsStore = EventsStore(context)

}