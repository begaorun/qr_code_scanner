package com.yukle.qrcodescanner.data.netwrok

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor2 : Interceptor {
    companion object {
        const val TAG = "AuthInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e(TAG, "intercept: ${chain.request().url}" )
        Log.e(TAG, "intercept: ${chain.request().headers}" )
        return chain.proceed(chain.request().newBuilder().build())
    }




}