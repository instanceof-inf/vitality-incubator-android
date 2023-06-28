package com.johan.blignaut.discovery.vitalityincubator.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClientInstance {
    lateinit var retrofit: Retrofit

//    private const val BASE_URL = "http://10.0.2.2:8080/"
    private const val BASE_URL = "http://localhost:8080/"

    /**
     * This was the other api I tried which had images, but the images were of wrong file type so Picasso could not display them
     */
//    private const val BASE_URL = "https://omgvamp-hearthstone-v1.p.rapidapi.com"
//    private var API_KEY = "e272f4ca80mshe74af57e9f4293ap1d4e7ajsn6762bcbe6403"

    val retrofitInstance: Retrofit
        get() {
            if (!this::retrofit.isInitialized) {
                val cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()
                val headersInterceptor = Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                    requestBuilder.removeHeader("Cache-Control")
                    requestBuilder.header("Cache-Control", cacheControl.toString())
//                    requestBuilder.header("X-RapidAPI-Key", API_KEY)
//                    requestBuilder.header("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com")
                    chain.proceed(requestBuilder.build())
                }

                val okHttpClient = OkHttpClient()
                    .newBuilder()
                    .followRedirects(true)
                    .addInterceptor(headersInterceptor)

                    // Attempted to use cache, but ran out of time to try and figure out how to access Context here
//                    .cache(Cache(File(cacheDir, "http-cache"), 10 * 1024 * 1024))

                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }
}