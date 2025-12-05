package com.tech.myapplication.domain.usecase

import com.tech.myapplication.data.model.ApiResponse
import com.tech.myapplication.data.utils.Resource
import com.tech.myapplication.domain.repository.NewsRepository

class GetNewsHeadlineUseCase (private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, page: Int): Resource<ApiResponse>{
        return newsRepository.getNewsHeadline(country, page)
    }
}