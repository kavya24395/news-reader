package com.kavya.newsreader.network.news.data

import com.google.gson.annotations.SerializedName

class AllNews {
    @SerializedName("status")
    val mStatus: String? = null

    @SerializedName("totalResults")
    val mTotalResult: Int = 0

    @SerializedName("articles")
    val mArticles: ArrayList<Article>? = null
}
