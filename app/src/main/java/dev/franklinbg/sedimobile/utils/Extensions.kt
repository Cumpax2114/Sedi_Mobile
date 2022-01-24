package dev.franklinbg.sedimobile.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

fun activateTextInputError(til: TextInputLayout, error: String = "campo abligatorio") {
    til.error = error
}

fun hayRed(activity: AppCompatActivity): Boolean =
    (activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).let {
        val networkInfo = it.activeNetwork
        val capabilities = it.getNetworkCapabilities(networkInfo)
        capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        ))
    }