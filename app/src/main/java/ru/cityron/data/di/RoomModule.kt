package ru.cityron.data.di

import android.content.Context
import androidx.room.Room
import com.fingerprintjs.android.fingerprint.FingerprinterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.cityron.data.room.all.alarms.M3AlarmsDatabase
import ru.cityron.data.room.all.algo.AlgoDatabase
import ru.cityron.data.room.all.sched.M3SchedDatabase
import ru.cityron.data.room.all.settings.M3SettingsDatabase
import ru.cityron.data.room.all.stat.M3StaticDatabase
import ru.cityron.data.room.all.state.M3StateDatabase
import ru.cityron.data.room.controller.ControllerDatabase
import ru.cityron.data.room.device.DeviceDatabase
import ru.cityron.data.room.events.EventDatabase
import ru.cityron.data.room.ip.IpDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideFingerprint(
        @ApplicationContext context: Context
    ) = FingerprinterFactory.create(context)

    @Provides
    @Singleton
    fun provideControllerDatabase(
        @ApplicationContext context: Context
    ): ControllerDatabase = Room.databaseBuilder(
        context = context,
        klass = ControllerDatabase::class.java,
        name = ControllerDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideIpDatabase(
        @ApplicationContext context: Context
    ): IpDatabase = Room.databaseBuilder(
        context = context,
        klass = IpDatabase::class.java,
        name = IpDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideEventDatabase(
        @ApplicationContext context: Context
    ): EventDatabase = Room.databaseBuilder(
        context = context,
        klass = EventDatabase::class.java,
        name = EventDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideAlarmsDatabase(
        @ApplicationContext context: Context
    ): M3AlarmsDatabase = Room.databaseBuilder(
        context = context,
        klass = M3AlarmsDatabase::class.java,
        name = M3AlarmsDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideSchedDatabase(
        @ApplicationContext context: Context
    ): M3SchedDatabase = Room.databaseBuilder(
        context = context,
        klass = M3SchedDatabase::class.java,
        name = M3SchedDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideSettingsDatabase(
        @ApplicationContext context: Context
    ): M3SettingsDatabase = Room.databaseBuilder(
        context = context,
        klass = M3SettingsDatabase::class.java,
        name = M3SettingsDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideStaticDatabase(
        @ApplicationContext context: Context
    ): M3StaticDatabase = Room.databaseBuilder(
        context = context,
        klass = M3StaticDatabase::class.java,
        name = M3StaticDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideStateDatabase(
        @ApplicationContext context: Context
    ): M3StateDatabase = Room.databaseBuilder(
        context = context,
        klass = M3StateDatabase::class.java,
        name = M3StateDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideAlgoDatabase(
        @ApplicationContext context: Context
    ): AlgoDatabase = Room.databaseBuilder(
        context = context,
        klass = AlgoDatabase::class.java,
        name = AlgoDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDeviceDatabase(
        @ApplicationContext context: Context
    ): DeviceDatabase = Room.databaseBuilder(
        context = context,
        klass = DeviceDatabase::class.java,
        name = DeviceDatabase.NAME
    ).fallbackToDestructiveMigration().build()

}