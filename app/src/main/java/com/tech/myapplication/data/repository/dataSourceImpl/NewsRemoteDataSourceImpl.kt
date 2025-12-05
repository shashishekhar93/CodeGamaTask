package com.tech.myapplication.data.repository.dataSourceImpl

import com.tech.myapplication.data.api.NewsApiService
import com.tech.myapplication.data.model.ApiResponse
import com.tech.myapplication.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(val newsApiService: NewsApiService) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(
        country: String,
        page: Int
    ): Response<ApiResponse> {
        return newsApiService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<ApiResponse> {
        return newsApiService.getSearchedTopHeadlines(country, searchQuery, page)
    }
}