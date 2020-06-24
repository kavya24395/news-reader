package com.kavya.newsreader.model.network.news.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_table")
class Article(

    @Embedded(prefix = "Source")
    @SerializedName("source")
    var mSource: Source,

    @ColumnInfo(name = "Title")
    @SerializedName("title")
    var mTitle: String,

    @ColumnInfo(name = "ImageURL")
    @SerializedName("urlToImage")
    var mImageUrl: String,

    @ColumnInfo(name = "Time")
    @SerializedName("publishedAt")
    var mPublishedAt: String,

    @ColumnInfo(name = "Description")
    @SerializedName("description")
    var mDescription: String


) : Parcelable {
    constructor(parcel: Parcel? = null) : this(Source("", ""), "", "", "", "") {
        val name = parcel?.readString().toString()
        val id = parcel?.readString().toString()
        mSource = Source(id, name)
        mTitle = parcel?.readString().toString()
        mImageUrl = parcel?.readString().toString()
        mPublishedAt = parcel?.readString().toString()
        mDescription = parcel?.readString().toString()

    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_ID")
    var id: Int = 0

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
        dest?.writeString(mTitle)
        dest?.writeString(mImageUrl)
        dest?.writeString(mPublishedAt)
        dest?.writeString(mDescription)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

}
