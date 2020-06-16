package com.kavya.newsreader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kavya.newsreader.model.NewsNetworkManager.fetchNewsOnline
import com.kavya.newsreader.model.network.news.data.AllNews
import com.kavya.newsreader.model.network.news.data.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kavya P S on 16/06/20.
 */

class NewsViewModel : ViewModel() {
    companion object {
        const val TAG: String = "NewsViewModel"
        val mCompositeDisposable = CompositeDisposable()
    }


    fun fetchLatestNews(): LiveData<List<Article>> {
        fetchFromNetwork()

        return MutableLiveData()
    }

    private fun fetchFromNetwork() {
        mCompositeDisposable.add(
            fetchNewsOnline()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(allNews: AllNews) {
        println("success ${allNews.mTotalResult}")
    }

    private fun handleError(throwable: Throwable) {
        println("error response")

    }


    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }

}