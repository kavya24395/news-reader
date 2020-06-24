package com.kavya.newsreader.utils

import android.view.View

/**z
 * Created by Kavya P S on 17/06/20.
 */
interface Launcher<T> {
    fun launchActivity(data: T, viewsToBeAnimated: List<View>?)
}