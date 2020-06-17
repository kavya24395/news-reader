package com.kavya.newsreader.model.network.news.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Article() : Parcelable {
    constructor(parcel: Parcel? = null) : this() {
        mSource = Source()
        mSource?.mName = parcel?.readString()
        mSource?.mId = parcel?.readString()
        mAuthor = parcel?.readString()
        mTitle = parcel?.readString()
        mDescription = parcel?.readString()
        mImageUrl = parcel?.readString()
        mPublishedAt = parcel?.readString()
        mContent = parcel?.readString()

    }

    @SerializedName("source")
    var mSource: Source? = null

    @SerializedName("author")
    var mAuthor: String? = null

    @SerializedName("title")
    var mTitle: String? = null

    @SerializedName("description")
    var mDescription: String? = null

    @SerializedName("urlToImage")
    var mImageUrl: String? = null

    @SerializedName("publishedAt")
    var mPublishedAt: String? = null

    @SerializedName("content")
    var mContent: String? = null


    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Article> = object : Parcelable.Creator<Article> {
            override fun createFromParcel(parcel: Parcel): Article? {
                return Article(parcel)
            }

            override fun newArray(size: Int): Array<Article?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(mSource?.mName)
        dest?.writeString(mSource?.mId)
        dest?.writeString(mAuthor)
        dest?.writeString(mTitle)
        dest?.writeString(mDescription)
        dest?.writeString(mImageUrl)
        dest?.writeString(mPublishedAt)
        dest?.writeString(mContent)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

}
