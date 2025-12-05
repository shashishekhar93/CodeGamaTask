package com.tech.myapplication.domain.usecase

import com.tech.myapplication.data.model.Article
import com.tech.myapplication.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNewsFromDB()
    }
}