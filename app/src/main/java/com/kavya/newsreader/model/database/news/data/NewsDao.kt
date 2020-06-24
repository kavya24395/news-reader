package com.kavya.newsreader.model.database.news.data;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kavya.newsreader.model.network.news.data.Article
import io.reactivex.Single


/**
 * Created by Kavya P S on 17/06/20.
 */
@Dao
interface NewsDao {
    @Query("SELECT * FROM news_table")
    fun fetchAllNews(): Single<List<Article>>

    @Transaction
    fun deleteAndCreate(articles: List<Article>): List<Long> {
        clearNews()
        return insertAll(articles)
    }

    @Query("DELETE FROM news_table")
    fun clearNews()

    @Insert
    fun insertAll(articles: List<Article>): List<Long>
}