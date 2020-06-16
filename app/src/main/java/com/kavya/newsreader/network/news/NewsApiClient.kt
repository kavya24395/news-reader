package com.kavya.newsreader.network.news

import com.kavya.newsreader.network.news.data.AllNews
import retrofit2.Call
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
    ): Call<AllNews>
}