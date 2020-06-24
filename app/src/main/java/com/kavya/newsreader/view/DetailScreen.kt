package com.kavya.newsreader.view

import android.os.Bundle
import android.transition.Explode
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kavya.newsreader.R
import com.kavya.newsreader.model.network.news.data.Article
import com.kavya.newsreader.utils.DateStringManipulator


class DetailScreen : AppCompatActivity() {

    var content: TextView? = null
    var headLine: TextView? = null
    var date: TextView? = null
    var source: TextView? = null
    var image: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)

        val article: Article? = intent.getParcelableExtra("newsArticle")
        headLine = findViewById(R.id.headline)
        date = findViewById(R.id.date)
        source = findViewById(R.id.source)
        content = findViewById(R.id.description)
        image = findViewById(R.id.full_image)

        source?.text = article?.mSource?.mName
        headLine?.text = article?.mTitle
        article?.mPublishedAt?.let {
            date?.text = DateStringManipulator.dateTimezoneRemover(it)
        }
        content?.text = article?.mDescription

        image?.let {

            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_broken_image_black_24dp)

            Glide
                .with(this)
                .load(article?.mImageUrl)
                .apply(options)
                .into(it)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }
}
