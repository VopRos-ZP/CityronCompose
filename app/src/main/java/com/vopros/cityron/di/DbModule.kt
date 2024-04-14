package com.vopros.cityron.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.cityron.core.data.local.ControllerDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideControllerDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, ControllerDatabase::class.java, "controller-db"
    ).build()

}