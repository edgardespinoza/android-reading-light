package com.eespinor.lightreading.reading.di

import com.eespinor.lightreading.reading.data.repository.ReadingRemoteRepository
import com.eespinor.lightreading.reading.data.repository.RoomRemoteRepository
import com.eespinor.lightreading.reading.domain.reading.repository.ReadingRepository
import com.eespinor.lightreading.reading.domain.repository.RoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindReadingRemoteRepository(repository: ReadingRemoteRepository): ReadingRepository

    @Binds
    @Singleton
    abstract fun bindRoomRemoteRepository(repository: RoomRemoteRepository): RoomRepository


}