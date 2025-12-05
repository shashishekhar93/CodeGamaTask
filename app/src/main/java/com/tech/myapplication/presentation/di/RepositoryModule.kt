package com.tech.myapplication.presentation.di

import com.tech.myapplication.data.repository.dataSource.NewsLocalDataSource
import com.tech.myapplication.data.repository.dataSource.NewsRemoteDataSource
import com.tech.myapplication.domain.repository.NewsRepository
import com.tech.myapplication.domain.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(
            newsRemoteDataSource,
            newsLocalDataSource
        )
    }
}