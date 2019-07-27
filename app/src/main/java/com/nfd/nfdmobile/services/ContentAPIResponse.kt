package com.nfd.nfdmobile.services

import com.google.gson.annotations.SerializedName

class NFDArticlesData {
    @SerializedName("data")
    var data: NFDArticlesResponse? = null
}

// Articles
class NFDArticlesResponse {
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("articles")
    var articles: List<NFDTextResponse>? = null
}

// Practices
class NFDPracticesData {
    @SerializedName("data")
    var data: NFDPracticesResponse? = null
}

class NFDPracticesResponse {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("practices")
    var practices: List<NFDTextResponse>? = null
}

class NFDTextResponse {
    @SerializedName("id")
    var id: String? = null

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
class NFDMeditationsData {
    @SerializedName("data")
    var data: NFDMeditationsResponse? = null
}

class NFDMeditationsResponse {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("meditations")
    var meditations: List<NFDAudioResponse>? = null
}

// Podcasts
class NFDPodcastsData {
    @SerializedName("data")
    var data: NFDPodcastsResponse? = null
}

class NFDPodcastsResponse {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("podcasts")
    var podcasts: List<NFDAudioResponse>? = null
}

class NFDAudioResponse {
    @SerializedName("id")
    var id: String? = null

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
