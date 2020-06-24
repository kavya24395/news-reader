package com.kavya.newsreader.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar
import com.kavya.newsreader.R
import com.kavya.newsreader.model.network.news.data.Article
import com.kavya.newsreader.utils.Launcher
import com.kavya.newsreader.view.adapter.NewsAdapter
import com.kavya.newsreader.viewmodel.NewsViewModel
import com.kavya.newsreader.viewmodel.data.ErrorType
import com.kavya.newsreader.viewmodel.data.Status

class MainActivity : AppCompatActivity(), Launcher<Article> {
    private lateinit var rootView: View
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var loading: LottieAnimationView
    private lateinit var status: TextView
    private lateinit var error: LottieAnimationView
    private val mNewsViewModel: NewsViewModel by viewModels()
    private lateinit var articleRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootView = findViewById(R.id.parent_layout)
        swipeRefresh = findViewById(R.id.swipe_refresh)
        loading = findViewById(R.id.loading)
        error = findViewById(R.id.error)

        articleRecyclerView = findViewById(R.id.article_list)
        articleRecyclerView.layoutManager = LinearLayoutManager(this)

        if (articleRecyclerView.adapter == null) {
            articleRecyclerView.adapter = NewsAdapter(ArrayList(), this)
        }
        if (mNewsViewModel.mCachedNews.size > 0) {
            updateNewsList(mNewsViewModel.mCachedNews)
        }
        observeForNews()
        swipeRefresh.setOnRefreshListener {
            println("Refresh is called")
            mNewsViewModel.refreshNews()
        }
    }

    private fun updateNewsList(updatedList: ArrayList<Article>) {
        if (articleRecyclerView.visibility != View.VISIBLE)
            articleRecyclerView.visibility = View.VISIBLE

        println("notifyDataSetChanged")
        (articleRecyclerView.adapter as NewsAdapter).articleList = updatedList
        articleRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun observeForNews() {
        mNewsViewModel.fetchLatestNews()
            .observe(this, Observer() {
                when (it.status) {
                    Status.SUCCESS -> {
                        println("Success")
                        loading.visibility = View.GONE
                        if (it.list.size > 0) {
                            println("Success size >0")
                            updateNewsList(it.list)
                            if (error.visibility == View.VISIBLE)
                                error.visibility = View.GONE
                        } else {
                            println("Success size == 0")
                            if (error.visibility != View.VISIBLE)
                                error.visibility = View.VISIBLE
                        }
                        if (swipeRefresh.isRefreshing) {
                            swipeRefresh.isRefreshing = false
                        } else {
                            Snackbar.make(rootView, "Pull to refresh", Snackbar.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        val displayMessage: String =
                            when (it.error?.errorType) {
                                ErrorType.NETWORK_ERROR -> "Check internet connectivity"
                                ErrorType.DATA_ERROR -> "Pull to Retry"
                                ErrorType.DB_ERROR -> "If the issue persists, restart the application"
                                ErrorType.SERVER_ERROR -> "Server error: Try again later"
                                else -> "Something went wrong"
                            }
                        if (swipeRefresh.isRefreshing) {
                            swipeRefresh.isRefreshing = false
                        }
                        if (mNewsViewModel.mCachedNews.size == 0
                            && loading.visibility != View.VISIBLE
                        ) {
                            error.visibility = View.VISIBLE
                        }
                        loading.visibility = View.GONE
                        Snackbar.make(rootView, displayMessage, Snackbar.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        if (!swipeRefresh.isRefreshing)
                            if (loading.visibility != View.VISIBLE)
                                loading.visibility = View.VISIBLE
//                            Snackbar.make(rootView, "Loading", Snackbar.LENGTH_SHORT).show()
                    }
                }

            })

    }

    override fun launchActivity(data: Article, invoker: List<View>?) {
        val intent = Intent(this, DetailScreen::class.java)
        intent.putExtra("newsArticle", data)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            invoker?.let {
                val option = ActivityOptions
                    .makeSceneTransitionAnimation(
                        this,
                        android.util.Pair(invoker[0], "article_image"),
                        android.util.Pair(invoker[1], "article_title"),
                        android.util.Pair(invoker[2], "article_src"),
                        android.util.Pair(invoker[3], "article_date")
                    )
                startActivity(intent, option.toBundle())
                return
            }
        }
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        mNewsViewModel.storePersistentCache()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
