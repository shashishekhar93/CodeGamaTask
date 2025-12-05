package com.tech.myapplication.domain.usecase

import com.tech.myapplication.data.model.ApiResponse
import com.tech.myapplication.data.utils.Resource
import com.tech.myapplication.domain.repository.NewsRepository

class GetSearchedNewsUseCase(val newsRepository: NewsRepository) {

    suspend fun execute(country:String,searchQuery: String, page: Int): Resource<ApiResponse>{
        return newsRepository.getSearchedNews(country,searchQuery,page)
    }
}