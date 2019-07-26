package com.nfd.nfdmobile.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



object ContentAPIService {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://neverfapdeluxe.netlify.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val contentApi: ContentAPIInterface = getRetrofit().create(ContentAPIInterface::class.java)
}
