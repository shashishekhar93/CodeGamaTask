package com.tech.myapplication.domain.usecase

import com.tech.myapplication.data.model.Article
import com.tech.myapplication.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}