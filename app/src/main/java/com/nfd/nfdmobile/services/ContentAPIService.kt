package com.nfd.nfdmobile.services

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ContentAPIService {
    @GET("content_articles/index.json")
    fun getArticles() : Call<NFDArticlesData>

    @GET("content_practices/index.json")
    fun getPractices() : Call<NFDPracticesData>

//    @GET("content_meditations/index.json")
//    fun getPodcasts() : Call<NFDPodcastsResponse>
//
//    @GET("content_podcasts/index.json")
//    fun getMeditations() : Call<NFDMeditationsResponse>

    companion object Factory {
        val BASE_URL = "https://neverfapdeluxe.netlify.com/"

        fun create(): ContentAPIService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ContentAPIService::class.java)
        }
    }
}
