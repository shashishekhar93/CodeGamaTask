package com.tech.myapplication.data.repository.dataSourceImpl

import com.tech.myapplication.data.db.ArticleDAO
import com.tech.myapplication.data.model.Article
import com.tech.myapplication.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(val articleDAO: ArticleDAO) : NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getAllArticles()
    }

    override suspend fun deleteArticlesFromDB(article: Article) {
        articleDAO.deleteArticle(article)
    }
}