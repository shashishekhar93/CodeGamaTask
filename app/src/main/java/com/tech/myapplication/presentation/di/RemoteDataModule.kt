package com.tech.myapplication.presentation.di

import com.tech.myapplication.data.api.NewsApiService
import com.tech.myapplication.data.repository.dataSource.NewsRemoteDataSource
import com.tech.myapplication.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(newsApiService: NewsApiService): NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsApiService)
    }
}