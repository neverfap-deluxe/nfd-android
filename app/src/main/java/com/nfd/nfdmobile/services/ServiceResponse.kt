package com.nfd.nfdmobile.services

import com.google.gson.annotations.SerializedName

class NFDArticlesData {
    @SerializedName("data")
    var data: NFDArticlesResponse? = null
}

// Articles
class NFDArticlesResponse {
//    @SerializedName("id")
//    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("articles")
    var articles = ArrayList<ArticleResponse>()
}

class ArticleResponse {
//    @SerializedName("id")
//    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null
}

// Practices
class NFDPracticesData {
    @SerializedName("data")
    var data: NFDPracticesResponse? = null
}

class NFDPracticesResponse {
//    @SerializedName("id")
//    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("practices")
    var practices: ArrayList<PracticeResponse>? = null
}

class PracticeResponse {
//    @SerializedName("id")
//    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null
}

// Meditations
class NFDMeditationsResponse {
//    @SerializedName("id")
//    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("meditations")
    var meditations: ArrayList<MeditationResponse>? = null
}

class MeditationResponse {
//    @SerializedName("id")
//    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("mp3Url")
    var mp3Url: String? = null
}

// Podcasts
class NFDPodcastsResponse {
//    @SerializedName("id")
//    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("podcasts")
    var podcasts: ArrayList<PodcastResponse>? = null
}

class PodcastResponse {
//    @SerializedName("id")
//    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("mp3Url")
    var mp3Url: String? = null
}
