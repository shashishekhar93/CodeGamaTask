package com.tech.myapplication.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tech.myapplication.data.model.ApiResponse
import com.tech.myapplication.data.model.Article
import com.tech.myapplication.data.utils.Resource
import com.tech.myapplication.domain.usecase.DeleteSavedNewsUseCase
import com.tech.myapplication.domain.usecase.GetNewsHeadlineUseCase
import com.tech.myapplication.domain.usecase.GetSavedNewsUseCase
import com.tech.myapplication.domain.usecase.GetSearchedNewsUseCase
import com.tech.myapplication.domain.usecase.SaveNewsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlineUseCase: GetNewsHeadlineUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : AndroidViewModel(application = app) {

    val newsHeadlines: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    fun getNewsHeadlines(country: String, page: Int) = viewModelScope.launch {
        //set loading state
        newsHeadlines.postValue(Resource.Loading())
        try {
            if (isInternetAvailable(app)) {
                val apiResult = getNewsHeadlineUseCase.execute(country, page)
                //set success state
                //save in db now
                if (apiResult is Resource.Success) {
                    apiResult.data?.articles?.forEach { article ->
                        saveNewsUseCase.execute(article)
                    }
                }
                newsHeadlines.postValue(apiResult)
            } else {
                //set error state
                //newsHeadlines.postValue(Resource.Error("Please Check your internet connection."))
                //load from cache store in db
                loadNewsFromCache()
            }
        } catch (e: Exception) {
            //set error state
            //newsHeadlines.postValue(Resource.Error(e.message.toString()))
            loadNewsFromCache()
        }
    }

    fun loadNewsFromCache() = viewModelScope.launch {
        try {
            val savedArticles = getSavedNewsUseCase.execute().first()
            if (savedArticles.isNotEmpty()){
                //here if cache is available then wrap in dummy api response. and provide it.
                val cacheResponse = ApiResponse(savedArticles,"offline",savedArticles.size)
                newsHeadlines.postValue(Resource.Success(cacheResponse))
            }else{
                newsHeadlines.postValue(Resource.Error("Please Check your internet connection."))
            }
        }catch (e: Exception){
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.run {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return result
    }


    //search operation.... add code below......
    val searchedNews: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    fun searchNews(country: String, searchQuery: String, page: Int) = viewModelScope.launch {
        try {
            if (isInternetAvailable(app)) {
                val response = getSearchedNewsUseCase.execute(country, searchQuery, page)
                searchedNews.postValue(response)
            } else {
                searchedNews.postValue(Resource.Error("Please Check your internet connection."))
            }
        } catch (e: Exception) {
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }
    }


    //database operations. now we need to add the remote data in local database for caching.

    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteNews(article: Article) = viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)
    }
}