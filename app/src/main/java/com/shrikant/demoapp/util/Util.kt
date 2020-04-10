package com.shrikant.demoapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.shrikant.demoapp.basic.App

object Util {

    fun checkNetwork(): Boolean {
        val connectivityManager =
            App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}