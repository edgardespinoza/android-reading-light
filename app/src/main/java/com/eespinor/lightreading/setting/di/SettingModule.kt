package com.eespinor.lightreading.setting.di

import android.app.Application
import androidx.room.Room
import com.eespinor.lightreading.reading.data.local.LightReadingDatabase
import com.eespinor.lightreading.reading.data.local.SettingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingModule {

    @Provides
    @Singleton
    fun provideLightReadingDatabase(app: Application): LightReadingDatabase {
        return Room.databaseBuilder(
            app, LightReadingDatabase::class.java, "lightReading.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSettingDao(database: LightReadingDatabase): SettingDao {
        return database.dao
    }
}
