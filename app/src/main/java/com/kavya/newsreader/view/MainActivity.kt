package com.kavya.newsreader.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kavya.newsreader.R
import com.kavya.newsreader.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity() {
    private val mNewsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeForNews()
    }

    private fun observeForNews() {
        mNewsViewModel.fetchLatestNews().observe(this, Observer() {
//            println("Fetched from live data ${it[0].mImageUrl}")
        })

    }
}
