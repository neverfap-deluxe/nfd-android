package com.nfd.nfdmobile.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


class Helpers {

    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (Build.VERSION.SDK_INT > 22) {
                val an = cm.activeNetwork ?: return false
                val capabilities = cm.getNetworkCapabilities(an) ?: return false
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else {
                val a = cm.activeNetworkInfo ?: return false
                a.isConnected && (a.type == ConnectivityManager.TYPE_WIFI || a.type == ConnectivityManager.TYPE_MOBILE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

}