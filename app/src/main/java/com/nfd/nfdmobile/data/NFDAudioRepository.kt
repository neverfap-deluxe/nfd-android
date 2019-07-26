package com.nfd.nfdmobile.data

import com.nfd.nfdmobile.services.ContentAPIInterface
import com.nfd.nfdmobile.services.NFDAudioResponse
import com.nfd.nfdmobile.services.NFDTextResponse

class NFDAudioRepository(
    private val contentApiInterface: ContentAPIInterface
) : BaseRepository() {

    suspend fun getMeditations() : MutableList<NFDAudioResponse>?{
        return safeApiCall(
            //await the result of deferred type
            call = {contentApiInterface.getMeditations().await()},
            error = "Error fetching news"
            //convert to mutable list
        )?.data?.meditations?.toMutableList()
    }

    suspend fun getPodcasts() : MutableList<NFDAudioResponse>?{
        return safeApiCall(
            //await the result of deferred type
            call = {contentApiInterface.getPodcasts().await()},
            error = "Error fetching news"
            //convert to mutable list
        )?.data?.podcasts?.toMutableList()
    }
}