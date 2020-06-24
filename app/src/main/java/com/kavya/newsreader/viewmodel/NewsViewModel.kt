package com.kavya.newsreader.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kavya.newsreader.model.NewsNetworkManager.fetchNewsOnline
import com.kavya.newsreader.model.database.NewsDBManager
import com.kavya.newsreader.model.network.news.data.AllNews
import com.kavya.newsreader.model.network.news.data.Article
import com.kavya.newsreader.utils.NetworkCheck
import com.kavya.newsreader.viewmodel.data.Error
import com.kavya.newsreader.viewmodel.data.ErrorType
import com.kavya.newsreader.viewmodel.data.Response
import com.kavya.newsreader.viewmodel.data.Status
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

/**
 * Created by Kavya P S on 16/06/20.
 */

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private var contextWeakRef: WeakReference<Context> =
        WeakReference<Context>(application.applicationContext)
    var mCachedNews = ArrayList<Article>()
    private val mCompositeDisposable = CompositeDisposable()
    private val mResponseLiveData = MutableLiveData<Response<Article>>()
    private lateinit var mNewsDBManager: NewsDBManager

    init {
        contextWeakRef.get()?.let {
            mNewsDBManager = NewsDBManager.getInstance(it)
        }
    }

    companion object {
        const val TAG: String = "NewsViewModel"
    }

    fun refreshNews() {

        updateLoadingState()
        if (NetworkCheck.isConnected(contextWeakRef)) {
            fetchFromNetwork()
        } else {
            updateErrorState(ErrorType.NETWORK_ERROR, "Check internet connectivity")
        }
    }

    fun fetchLatestNews(): LiveData<Response<Article>> {
        updateLoadingState()
        if (NetworkCheck.isConnected(contextWeakRef)) {
            fetchFromNetwork()
        } else {
            contextWeakRef.get()?.let {
                fetchFromDB()
            }
        }
        return mResponseLiveData
    }

    private fun fetchFromDB() {

        mCompositeDisposable.add(
            mNewsDBManager
                .fetchCachedNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleDBResponse, this::handleDBError)
        )
    }

    private fun fetchFromNetwork() {
        mCompositeDisposable.add(
            fetchNewsOnline()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleNetworkResponse, this::handleNetworkError)
        )
    }


    private fun handleNetworkResponse(allNews: AllNews) {

        val response = Response<Article>()
        if (allNews.mTotalResult >= 0) {
            response.status = Status.SUCCESS
            response.list = when (allNews.mArticles) {
                null -> ArrayList()
                else -> allNews.mArticles
            }
            if (response.list.size > 0)
                mCachedNews = response.list
        } else {
            response.status = Status.ERROR

            response.error =
                Error("'totalResult' value from Server is negative.", ErrorType.DATA_ERROR)
        }
        mResponseLiveData.value = response
    }


    private fun handleNetworkError(throwable: Throwable) {

        val errorType: ErrorType = if (!NetworkCheck.isConnected(contextWeakRef)) {
            ErrorType.NETWORK_ERROR
        } else {
            ErrorType.SERVER_ERROR
        }

        updateErrorState(errorType, throwable.message ?: "No valid error message from Server")


    }

    private fun updateErrorState(errorType: ErrorType, message: String) {
        val response = Response<Article>()
        response.status = Status.ERROR

        response.error =
            Error(
                "Error response from API - ${errorType.name} || Reason: $message",
                errorType
            )
        mResponseLiveData.value = response
    }

    private fun updateLoadingState() {
        val response = Response<Article>()
        response.status = Status.LOADING
        mResponseLiveData.value = response
    }

    private fun handleDBResponse(articles: List<Article>) {
        val response = Response<Article>()
        if (articles.size >= 0) {
            response.status = Status.SUCCESS
            response.list = ArrayList()
            for (article: Article in articles) {
                response.list.add(article)
            }
            if (response.list.size > 0)
                mCachedNews = response.list
        } else {
            response.status = Status.ERROR
            response.error =
                Error("'totalResult' value from DB is negative.", ErrorType.DATA_ERROR)
        }
        mResponseLiveData.value = response
    }

    private fun handleDBError(throwable: Throwable) {

        updateErrorState(ErrorType.DB_ERROR, throwable.message ?: "No valid error message from DB")

    }


    fun storePersistentCache() {
        if (mCachedNews.size > 0)
            mCompositeDisposable.add(
                Single.just(mNewsDBManager.newsDao)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        println("inserted ${mNewsDBManager.clearAndBatchInsertNews(mCachedNews).size}) entries.")
                    }, {}
                    )
            )
    }

    override fun onCleared() {
        super.onCleared()
        clearResource()
    }

    private fun clearResource() {
        mCompositeDisposable.dispose()
        mCachedNews.clear()
    }

}