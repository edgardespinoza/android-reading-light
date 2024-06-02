package com.eespinor.lightreading.setting.di

import com.eespinor.lightreading.setting.data.repository.SettingRepositoryLocal
import com.eespinor.lightreading.setting.domain.repository.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositorySettingModule {

    @Binds
    @Singleton
    abstract fun bindSettingRemoteRepository(repository: SettingRepositoryLocal): SettingRepository


}