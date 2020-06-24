package com.kavya.newsreader.model.database

import android.content.Context
import com.kavya.newsreader.model.database.news.data.NewsDao
import com.kavya.newsreader.model.database.news.data.NewsDatabase
import com.kavya.newsreader.model.network.news.data.Article
import io.reactivex.Single

/**
 * Created by Kavya P S on 17/06/20.
 */
class NewsDBManager private constructor(context: Context) {
    val newsDao: NewsDao = NewsDatabase.getDatabase(context).newsDao()

    companion object {
        private var instance: NewsDBManager? = null

        fun getInstance(context: Context): NewsDBManager {
            if (instance == null)  // NOT thread safe!
            {
                instance = NewsDBManager(context)
            }

            return instance!!
        }
    }

    fun fetchCachedNews(): Single<List<Article>> {
        return newsDao.fetchAllNews()
    }

    fun clearAndBatchInsertNews(articleList: List<Article>): List<Long> {
        return newsDao.deleteAndCreate(articleList)
    }

    fun clearCachedNews() {
        newsDao.clearNews()
    }

}