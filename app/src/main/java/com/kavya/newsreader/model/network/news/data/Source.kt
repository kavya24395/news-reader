package com.kavya.newsreader.model.network.news.data

import com.google.gson.annotations.SerializedName

class Source {
    @SerializedName("id")
    var mId: String? = null

    @SerializedName("name")
    var mName: String? = null
}
