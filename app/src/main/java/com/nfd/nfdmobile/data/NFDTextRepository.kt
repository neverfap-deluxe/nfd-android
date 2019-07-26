package com.nfd.nfdmobile.data

import com.nfd.nfdmobile.services.ContentAPIInterface
import com.nfd.nfdmobile.services.NFDTextResponse

class NFDTextRepository(
    private val contentApiInterface: ContentAPIInterface
) : BaseRepository() {

    suspend fun getArticles() : MutableList<NFDTextResponse>?{
        return safeApiCall(
            //await the result of deferred type
            call = {contentApiInterface.getArticles().await()},
            error = "Error fetching news"
            //convert to mutable list
        )?.data?.articles?.toMutableList()
    }

    suspend fun getPractices() : MutableList<NFDTextResponse>?{
        return safeApiCall(
            //await the result of deferred type
            call = {contentApiInterface.getPractices().await()},
            error = "Error fetching news"
            //convert to mutable list
        )?.data?.practices?.toMutableList()
    }
}