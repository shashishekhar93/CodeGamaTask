package com.tech.myapplication.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tech.myapplication.domain.usecase.DeleteSavedNewsUseCase
import com.tech.myapplication.domain.usecase.GetNewsHeadlineUseCase
import com.tech.myapplication.domain.usecase.GetSavedNewsUseCase
import com.tech.myapplication.domain.usecase.GetSearchedNewsUseCase
import com.tech.myapplication.domain.usecase.SaveNewsUseCase

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlineUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app,
            getNewsHeadlinesUseCase,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            getSavedNewsUseCase,
            deleteSavedNewsUseCase
        ) as T
    }
}