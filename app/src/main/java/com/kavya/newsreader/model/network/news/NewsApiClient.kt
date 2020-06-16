package com.kavya.newsreader.model.network.news

import com.kavya.newsreader.model.network.news.data.AllNews
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Kavya P S on 15/06/20.
 */
//557245fd86c64c77b3c82aefb12192e4

interface NewsApiClient {

    @GET("top-headlines")
    fun fetchAllNews(
        @Query("apiKey") apiKey: String ="557245fd86c64c77b3c82aefb12192e4",
        @Query("country") country: String = "us"
    ): Single<AllNews>
}