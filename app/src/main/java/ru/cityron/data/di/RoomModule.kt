package ru.cityron.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.cityron.data.room.ControllerDatabase
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
    ).fallbackToDestructiveMigration() .build()

}