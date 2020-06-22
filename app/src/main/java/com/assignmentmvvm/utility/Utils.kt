package com.retrofit.utility

import android.R
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService


object Utils {
    internal fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        //Logger.d(classTag, "hasNetworkAvailable: ${(network != null)}")
        return (network != null)
    }
}