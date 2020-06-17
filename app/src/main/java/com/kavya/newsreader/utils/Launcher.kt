package com.kavya.newsreader.utils

/**
 * Created by Kavya P S on 17/06/20.
 */
interface Launcher<T> {
    fun launchActivity(data: T)
}