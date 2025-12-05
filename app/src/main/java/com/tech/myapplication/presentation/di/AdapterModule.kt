package com.tech.myapplication.presentation.di

import com.tech.myapplication.presentation.adapter.ChatAdapter
import com.tech.myapplication.presentation.adapter.FeaturedAdapter
import com.tech.myapplication.presentation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AdapterModule {

    @Singleton
    @Provides
    fun provideNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }

    @Singleton
    @Provides
    fun provideFeaturedAdapter(): FeaturedAdapter {
        return FeaturedAdapter()
    }

    @Singleton
    @Provides
    fun provideChatAdapter(): ChatAdapter{
        return ChatAdapter()
    }
}