package com.mindera.rocketscience.di.modules

import com.mindera.rocketscience.data.repository.LaunchesDataSource
import com.mindera.rocketscience.data.repository.LaunchesRepository
import com.mindera.rocketscience.data.repository.LaunchesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(dataSource: LaunchesDataSource) : LaunchesRepository{
        return LaunchesRepositoryImpl(dataSource)
    }
}