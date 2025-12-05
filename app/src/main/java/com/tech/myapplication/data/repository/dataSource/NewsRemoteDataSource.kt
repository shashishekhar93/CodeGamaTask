package com.tech.myapplication.data.repository.dataSource

import com.tech.myapplication.data.model.ApiResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, page:Int): Response<ApiResponse>
    suspend fun getSearchedNews(country: String, searchQuery: String,page: Int): Response<ApiResponse>
}