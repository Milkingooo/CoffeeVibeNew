package com.example.coffeevibe.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork

        return activeNetwork != null && connectivityManager.getNetworkCapabilities(activeNetwork)?.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}