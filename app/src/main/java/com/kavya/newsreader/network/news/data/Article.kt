package com.kavya.newsreader.network.news.data

import com.google.gson.annotations.SerializedName

class Article {
    @SerializedName("source")
    val mSource: Source? = null

    @SerializedName("author")
    val mAuthor: String? = null

    @SerializedName("title")
    val mTitle: String? = null

    @SerializedName("description")
    val mDescription: String? = null

    @SerializedName("urlToImage")
    val mImageUrl: String? = null

    @SerializedName("publishedAt")
    val mPublishedAt: String? = null

    @SerializedName("content")
    val mContent: String? = null
}
