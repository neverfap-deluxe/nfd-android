package com.nfd.nfdmobile.services

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET


interface  ContentAPIInterface {
    @GET("content_articles/index.json")
    fun getArticles() : Deferred<Response<NFDArticlesData>>

    @GET("content_practices/index.json")
    fun getPractices() : Deferred<Response<NFDPracticesData>>

    @GET("content_meditations/index.json")
    fun getMeditations() : Deferred<Response<NFDMeditationsData>>

    @GET("content_podcast/index.json")
    fun getPodcasts() : Deferred<Response<NFDPodcastsData>>
}
