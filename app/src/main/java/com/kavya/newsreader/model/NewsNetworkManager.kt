package com.kavya.newsreader.model

import com.kavya.newsreader.model.network.news.NewsApiClient
import com.kavya.newsreader.model.network.news.data.AllNews
import com.kavya.newsreader.utils.NetworkService
import io.reactivex.Single

/**
 * Created by Kavya P S on 16/06/20.
 */

object NewsNetworkManager {

    fun fetchNewsOnline(): Single<AllNews> {
        return NetworkService.getNetworkHook(NewsApiClient::class.java)
            .fetchAllNews()
    }
}