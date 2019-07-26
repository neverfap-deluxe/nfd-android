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
    val meditations = MutableLiveData<MutableList<NFDText>>()
    val podcasts = MutableLiveData<MutableList<NFDText>>()

    fun getLatestArticles(takeValue: Int) {
        val type = "article"
        scope.launch {
            val database = AppDatabase.getInstance(context)
            val nfdTextDao = database.nfdTextDao()
            val existingTexts = nfdTextDao.getTextsByType(type)

            val retrievedList = nfdTextRepository.getArticles()
            retrievedList?.let {
                val sortedList = populateDatabase(nfdTextDao, existingTexts, retrievedList, type, takeValue)
                articles.postValue(sortedList)
            }
        }
    }

    fun getLatestPractices(takeValue: Int) {
        val type = "practice"
        scope.launch {
            val database = AppDatabase.getInstance(context)
            val nfdTextDao = database.nfdTextDao()
            val existingTexts = nfdTextDao.getTextsByType(type)

            val retrievedList = nfdTextRepository.getPractices()

            retrievedList?.let {
                val sortedList = populateDatabase(nfdTextDao, existingTexts, retrievedList, type, takeValue)
                practices.postValue(sortedList)
            }
        }
    }

    fun getLatestMeditations(takeValue: Int) {
        val type = "meditation"
        scope.launch {
            val database = AppDatabase.getInstance(context)
            val nfdTextDao = database.nfdTextDao()
            val existingTexts = nfdTextDao.getTextsByType(type)

            val retrievedList = nfdAudioRepository.getMeditations()
            retrievedList?.let {
                val sortedList = populateAudioDatabase(nfdTextDao, existingTexts, retrievedList, type, takeValue)
                articles.postValue(sortedList)
            }
        }
    }

    fun getLatestPodcasts(takeValue: Int) {
        val type = "podcast"
        scope.launch {
            val database = AppDatabase.getInstance(context)
            val nfdTextDao = database.nfdTextDao()
            val existingTexts = nfdTextDao.getTextsByType(type)

            val retrievedList = nfdAudioRepository.getPodcasts()
            retrievedList?.let {
                val sortedList = populateAudioDatabase(nfdTextDao, existingTexts, retrievedList, type, takeValue)
                articles.postValue(sortedList)
            }
        }
    }

    private fun populateDatabase(nfdTextDao: NFDTextDao, existingTexts: List<NFDText>, retrievedList: List<NFDTextResponse>, type: String, takeValue: Int): MutableList<NFDText> {
        var newList: MutableList<NFDText> = existingTexts as MutableList<NFDText>

        if (existingTexts.size === 0) {
            // NOTE: I'm not sure if these need to be reversed.
            retrievedList.reversed().forEach {
                val newNFDTextItem = NFDText(0, it.title, it.date, it.content, type)
                nfdTextDao.insert(newNFDTextItem)
                newList.add(newNFDTextItem)
            }
            return newList.take(takeValue) as MutableList<NFDText>
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
        return newList.take(takeValue) as MutableList<NFDText>
    }

    private fun populateAudioDatabase(nfdTextDao: NFDTextDao, existingTexts: List<NFDText>, retrievedList: List<NFDAudioResponse>, type: String, takeValue: Int): MutableList<NFDText> {
        var newList: MutableList<NFDText> = existingTexts as MutableList<NFDText>

        if (existingTexts.size === 0) {
            // NOTE: I'm not sure if these need to be reversed.
            retrievedList.reversed().forEach {
                val newNFDTextItem = NFDText(0, it.title, it.date, it.content, type)
                nfdTextDao.insert(newNFDTextItem)
                newList.add(newNFDTextItem)
            }
            return newList.take(takeValue) as MutableList<NFDText>
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
        return newList.take(takeValue) as MutableList<NFDText>
    }


    fun cancelRequests() = coroutineContext.cancel()
}