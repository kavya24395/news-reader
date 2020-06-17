package com.kavya.newsreader.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.lang.ref.WeakReference

/**
 * Created by Kavya P S on 17/06/20.
 */
object NetworkCheck {
    fun getConnectivityStatusString(contextWeakRef: WeakReference<Context>): Boolean {
        val context = contextWeakRef.get()
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true;
    }
}
