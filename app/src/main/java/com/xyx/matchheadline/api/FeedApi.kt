package com.xyx.matchheadline.api

import com.xyx.matchheadline.BuildConfig
import com.xyx.matchheadline.vo.FeedsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FeedApi {

    companion object {
        val instance: FeedApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://firebasestorage.googleapis.com/")
                .client(
                    OkHttpClient.Builder().apply {
                        if (BuildConfig.DEBUG) {
                            addInterceptor(HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            })
                        }
                    }.build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FeedApi::class.java)
        }
    }

    @GET("v0/b/nca-dna-apps-dev.appspot.com/o/game.json?alt=media&token=e36c1a14-25d9-4467-8383-a53f57ba6bfe")
    suspend fun fetchFeeds(): FeedsResponse

}