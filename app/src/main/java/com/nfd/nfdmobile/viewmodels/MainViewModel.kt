package com.nfd.nfdmobile.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nfd.nfdmobile.data.AppDatabase
import com.nfd.nfdmobile.data.NFDText
import com.nfd.nfdmobile.data.NFDTextDao
import com.nfd.nfdmobile.data.NFDTextRepository
import com.nfd.nfdmobile.services.ContentAPIService
import com.nfd.nfdmobile.services.NFDTextResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// https://proandroiddev.com/oversimplified-network-call-using-retrofit-livedata-kotlin-coroutines-and-dsl-512d08eadc16

class MainViewModel(val context: Context) : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext : CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private val nfdTextRepository : NFDTextRepository = NFDTextRepository(ContentAPIService.contentApi)

    val articles = MutableLiveData<MutableList<NFDText>>()
    val practices = MutableLiveData<MutableList<NFDText>>()

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

    private fun populateDatabase(nfdTextDao: NFDTextDao, existingTexts: List<NFDText>, retrievedList: List<NFDTextResponse>, type: String): MutableList<NFDText> {
        var newList: MutableList<NFDText> = existingTexts as MutableList<NFDText>

        if (existingTexts.size === 0) {
            // NOTE: I'm not sure if these need to be reversed.
            retrievedList.reversed().forEach {
                val newNFDTextItem = NFDText(0, it.title, it.date, it.content, type)
                nfdTextDao.insert(newNFDTextItem)
                newList.add(newNFDTextItem)
            }
            return newList
        }
        for (retrievedText in retrievedList) {
            val doesTextExist = existingTexts.find {
                it.title == retrievedText.title
            }
            !doesTextExist.let {
                val newNFDTextItem = NFDText(0, it?.title, it?.date, it?.content, type)
                nfdTextDao.insert(newNFDTextItem)
                newList.add(newNFDTextItem)
            }
        }
        return newList
    }

    fun cancelRequests() = coroutineContext.cancel()
//
//  private fun loadArticles(context: Context): MutableLiveData<Resource<List<NFDText>>>  {
//      val type = "article"
//      val service = ContentAPIService.create()
//
//      val database = AppDatabase.getInstance(context)
//      val nfdTextDao = database.nfdTextDao()
//      val existingTexts = nfdTextDao.getTextsByType(type)
//
//      launch {
//          try {
//              val articlesResponse = service.getArticles().awaitResult().getOrThrow()
//
////          val articlesResponse = response.body()!!
//              val retrievedList = articlesResponse.data?.articles
//              retrievedList?.let {
//                  val sortedList = populateDatabase(nfdTextDao, existingTexts, retrievedList, type)
//                  articles.setValue(sortedList)
//              }
//
//          }
//          catch (e: Exception) {
//              articles.setValue(existingTexts)
//          }
//      }
//  }
//
//  private fun loadPractices(context: Context) {
//      val type = "practice"
//
//      val service = ContentAPIService.create()
//
//      service.getPractices().enqueue(object : Callback<NFDPracticesData> {
//          val database = AppDatabase.getInstance(context)
//          val nfdTextDao = database.nfdTextDao()
//          val existingTexts = nfdTextDao.getTextsByType(type)
//
//          override fun onResponse(call: Call<NFDPracticesData>, response: Response<NFDPracticesData>) {
//              if (response.code() == 200) {
//                  val practicesResponse = response.body()!!
//                  val retrievedList = practicesResponse.data?.practices
//                  retrievedList?.let {
//                      val sortedList = populateDatabase(nfdTextDao, existingTexts, retrievedList, type)
//                      practices.setValue(sortedList)
//                  }
//              }
//              practices.setValue(existingTexts)
//          }
//          override fun onFailure(call: Call<NFDPracticesData>, t: Throwable) {
//            practices.setValue(existingTexts)
//          }
//      })
//  }

}