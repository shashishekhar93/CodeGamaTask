package com.tech.myapplication.presentation.di

import android.app.Application
import com.tech.myapplication.domain.usecase.DeleteSavedNewsUseCase
import com.tech.myapplication.domain.usecase.GetNewsHeadlineUseCase
import com.tech.myapplication.domain.usecase.GetSavedNewsUseCase
import com.tech.myapplication.domain.usecase.GetSearchedNewsUseCase
import com.tech.myapplication.domain.usecase.SaveNewsUseCase
import com.tech.myapplication.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        app: Application,
        getNewsHeadlineUseCase: GetNewsHeadlineUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase,
        deleteSavedNewsUseCase: DeleteSavedNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            app, getNewsHeadlineUseCase,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            getSavedNewsUseCase,
            deleteSavedNewsUseCase
        )
    }
}