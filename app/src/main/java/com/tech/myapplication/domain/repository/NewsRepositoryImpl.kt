package com.tech.myapplication.domain.repository

import com.tech.myapplication.data.model.ApiResponse
import com.tech.myapplication.data.model.Article
import com.tech.myapplication.data.repository.dataSource.NewsLocalDataSource
import com.tech.myapplication.data.repository.dataSource.NewsRemoteDataSource
import com.tech.myapplication.data.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    val newsRemoteDataSource: NewsRemoteDataSource,
    val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {
    override suspend fun getNewsHeadline(
        country: String,
        page: Int
    ): Resource<ApiResponse> {
        return responseToResult(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<ApiResponse> {
        return responseToResult(newsRemoteDataSource.getSearchedNews(country, searchQuery, page))
    }


    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticlesFromDB(article)
    }

    override fun getSavedNewsFromDB(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }


    private fun responseToResult(response: Response<ApiResponse>): Resource<ApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { response ->
                return Resource.Success(response)
            }
        }
        return Resource.Error(response.message())
    }
}