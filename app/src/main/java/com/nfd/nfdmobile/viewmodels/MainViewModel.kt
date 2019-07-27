package com.nfd.nfdmobile.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nfd.nfdmobile.data.*
import com.nfd.nfdmobile.services.ContentAPIService
import com.nfd.nfdmobile.services.NFDAudioResponse
import com.nfd.nfdmobile.services.NFDTextResponse
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

// https://proandroiddev.com/oversimplified-network-call-using-retrofit-livedata-kotlin-coroutines-and-dsl-512d08eadc16

class MainViewModel(val context: Context) : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext : CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private val nfdTextRepository : NFDTextRepository = NFDTextRepository(ContentAPIService.contentApi)
    private val nfdAudioRepository : NFDAudioRepository = NFDAudioRepository(ContentAPIService.contentApi)

    val articles = MutableLiveData<MutableList<NFDText>>()
    val practices = MutableLiveData<MutableList<NFDText>>()
    val meditations = MutableLiveData<MutableList<NFDAudio>>()
    val podcasts = MutableLiveData<MutableList<NFDAudio>>()

    fun getLatestArticles() {
        val type = "article"
        scope.launch {
            val database = AppDatabase.getInstance(context)
            val nfdTextDao = database.nfdTextDao()
            val existingTexts = nfdTextDao.getTextsByType(type)

            val retrievedList = nfdTextRepository.getArticles()
            retrievedList?.let {
                val sortedList = populateDatabase(nfdTextDao, existingTexts, retrievedList, type)
                articles.postValue(sortedList)
            }
        }
    }

    fun getLatestPractices() {
        val type = "practice"
        scope.launch {
            val database = AppDatabase.getInstance(context)
            val nfdTextDao = database.nfdTextDao()
            val existingTexts = nfdTextDao.getTextsByType(type)

            val retrievedList = nfdTextRepository.getPractices()

            retrievedList?.let {
                val sortedList = populateDatabase(nfdTextDao, existingTexts, retrievedList, type)
                practices.postValue(sortedList)
            }
        }
    }

    fun getLatestMeditations() {
        val type = "meditation"
        scope.launch {
            val database = AppDatabase.getInstance(context)
            val nfdAudioDao = database.nfdAudioDao()
            val existingTexts = nfdAudioDao.getAudiosByType(type)

            val retrievedList = nfdAudioRepository.getMeditations()
            retrievedList?.let {
                val sortedList = populateAudioDatabase(nfdAudioDao, existingTexts, retrievedList, type)
                meditations.postValue(sortedList)
            }
        }
    }

    fun getLatestPodcasts() {
        val type = "podcast"
        scope.launch {
            val database = AppDatabase.getInstance(context)
            val nfdAudioDao = database.nfdAudioDao()
            val existingTexts = nfdAudioDao.getAudiosByType(type)

            val retrievedList = nfdAudioRepository.getPodcasts()
            retrievedList?.let {
                val sortedList = populateAudioDatabase(nfdAudioDao, existingTexts, retrievedList, type)
                podcasts.postValue(sortedList)
            }
        }
    }

    private fun populateDatabase(nfdTextDao: NFDTextDao, existingTexts: List<NFDText>, retrievedList: List<NFDTextResponse>, type: String): MutableList<NFDText> {
        var newList: MutableList<NFDText> = existingTexts as MutableList<NFDText>

        if (existingTexts.size === 0) {
            // NOTE: I'm not sure if these need to be reversed.
            retrievedList.reversed().forEach {
                val newNFDTextItem = NFDText(0, it.title, it.date, it.content, type)
                nfdTextDao.insert(newNFDTextItem)
                newList.add(newNFDTextItem)
            }
            return newList as MutableList<NFDText>
        }
        for (retrievedText in retrievedList) {
            val doesTextExist = existingTexts.find {
                it.title == retrievedText.title
            }
            if (doesTextExist == null) {
                val newNFDTextItem = NFDText(0, doesTextExist?.title, doesTextExist?.date, doesTextExist?.content, type)
                nfdTextDao.insert(newNFDTextItem)
                newList.add(newNFDTextItem)
            }
        }
        return newList as MutableList<NFDText>
    }

    private fun populateAudioDatabase(nfdAudioDao: NFDAudioDao, existingTexts: List<NFDAudio>, retrievedList: List<NFDAudioResponse>, type: String): MutableList<NFDAudio> {
        var newList: MutableList<NFDAudio> = existingTexts as MutableList<NFDAudio>

        if (existingTexts.size === 0) {
            // NOTE: I'm not sure if these need to be reversed.
            retrievedList.reversed().forEach {
                val newNFDAudioItem = NFDAudio(0, it.title, it.date, it.content, it.mp3Url, type)
                nfdAudioDao.insert(newNFDAudioItem)
                newList.add(newNFDAudioItem)
            }
            return newList as MutableList<NFDAudio>
        }
        for (retrievedText in retrievedList) {
            val doesTextExist = existingTexts.find {
                it.title == retrievedText.title
            }
            if (doesTextExist == null) {
                val newNFDAudioItem = NFDAudio(0, doesTextExist?.title, doesTextExist?.date, doesTextExist?.content, doesTextExist?.mp3Url, type)
                nfdAudioDao.insert(newNFDAudioItem)
                newList.add(newNFDAudioItem)
            }
        }
        return newList as MutableList<NFDAudio>
    }

    fun cancelRequests() = coroutineContext.cancel()
}