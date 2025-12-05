package com.tech.myapplication.domain.repository

import com.tech.myapplication.data.model.ApiResponse
import com.tech.myapplication.data.model.Article
import com.tech.myapplication.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    //this custom Resource class will give me the success, loading and error in response.
    suspend fun getNewsHeadline(country: String, page: Int): Resource<ApiResponse>

    suspend fun getSearchedNews(country: String,searchQuery: String,page: Int):Resource<ApiResponse>

    //lets create interfaces for local database operations..

    suspend fun saveNews(article: Article)

    suspend fun deleteNews(article: Article)

    fun getSavedNewsFromDB(): Flow<List<Article>>
}