package com.tech.myapplication.presentation.di

import com.tech.myapplication.domain.repository.NewsRepository
import com.tech.myapplication.domain.usecase.DeleteSavedNewsUseCase
import com.tech.myapplication.domain.usecase.GetNewsHeadlineUseCase
import com.tech.myapplication.domain.usecase.GetSavedNewsUseCase
import com.tech.myapplication.domain.usecase.GetSearchedNewsUseCase
import com.tech.myapplication.domain.usecase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetNewsHeadlinesUseCase(newsRepository: NewsRepository): GetNewsHeadlineUseCase {
        return GetNewsHeadlineUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchedNewsUseCase(newsRepository: NewsRepository): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(newsRepository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedNewsUseCase(newsRepository: NewsRepository): DeleteSavedNewsUseCase {
        return DeleteSavedNewsUseCase(newsRepository)
    }
}