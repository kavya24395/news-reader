package com.kavya.newsreader.viewmodel.data

/**
 * Created by Kavya P S on 16/06/20.
 */
class Response<T>(
    var status: Status = Status.LOADING,
    var list: ArrayList<T> = ArrayList(),
    var error: Error? = null
) {}