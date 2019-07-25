package com.nfd.nfdmobile.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nfd.nfdmobile.data.AppDatabase
import com.nfd.nfdmobile.data.NFDText
import com.nfd.nfdmobile.data.NFDTextDao
import com.nfd.nfdmobile.services.ContentAPIService
import com.nfd.nfdmobile.services.NFDArticlesData
import com.nfd.nfdmobile.services.NFDPracticesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// https://proandroiddev.com/oversimplified-network-call-using-retrofit-livedata-kotlin-coroutines-and-dsl-512d08eadc16


class MainViewModel : ViewModel() {

  private val articles: MutableLiveData<List<NFDText>> by lazy {
      MutableLiveData().also {
          loadArticles()
      }
  }

  private val practices: MutableLiveData<List<NFDText>> by lazy {
      MutableLiveData().also {
          loadPractices()
      }
  }

  fun getArticles(): LiveData<List<NFDText>> {
      return articles
  }

  fun getPractices(): LiveData<List<NFDText>> {
      return practices
  }

  private fun loadArticles(context: Context) {
      val type = "article"
      val database = AppDatabase.getInstance(context)
      val nfdTextDao = database.nfdTextDao()
      val existingTexts = nfdTextDao.getTextsByType(type)

      val service = ContentAPIService.create()

      service.getArticles().enqueue(object : Callback<NFDArticlesData> {
          override fun onResponse(call: Call<NFDArticlesData>, response: Response<NFDArticlesData>) {
              if (response.code() == 200) {
                  val articlesResponse = response.body()!!
                  val retrievedList = articlesResponse.data?.articles
                  val sortedList = populateDatabase(nfdTextDao, existingTexts, retrievedList, type)
                  articles.setValue(sortedList)
              }
              articles.setValue(existingTexts)
          }
          override fun onFailure(call: Call<NFDArticlesData>, t: Throwable) {
            articles.setValue(existingTexts)
          }
      })
  }

  private fun loadPractices(context: Context) {
      val type = "practice"
      val database = AppDatabase.getInstance(context)
      val nfdTextDao = database.nfdTextDao()
      val existingTexts = nfdTextDao.getTextsByType(type)

      val service = ContentAPIService.create()

      service.getPractices().enqueue(object : Callback<NFDPracticesData> {
          override fun onResponse(call: Call<NFDPracticesData>, response: Response<NFDPracticesData>) {
              if (response.code() == 200) {
                  val practicesResponse = response.body()!!
                  val retrievedList = practicesResponse.data?.practices
                  val sortedList = populateDatabase(nfdTextDao, existingTexts, retrievedList, type)
                  practices.setValue(sortedList)
              }
              practices.setValue(existingTexts)
          }
          override fun onFailure(call: Call<NFDPracticesData>, t: Throwable) {
            practices.setValue(existingTexts)
          }
      })
  }

  private fun populateDatabase(nfdTextDao: NFDTextDao, existingTexts: List<NFDText>, retrievedList: List<NFDText>, type: String): List<NFDText> {
    var newList: ArrayList<NFDText> = existingTexts as ArrayList<NFDText>
    
    if (existingTexts.size === 0) {
        // NOTE: I'm not sure if these need to be reversed.
        retrievedList.reversed().forEach {
            val newNFDTextItem = NFDText(0, it.title, it.date, it.content, it.type)
            nfdTextDao.insert(newNFDTextItem)
            newList.add(newNFDTextItem)
        }
        return newList
    }
    for (retrievedText in retrievedList) {
        val doesTextExist = existingTexts.find {
            it.title == retrievedText.title
        }
        !doesTextExist?.let {
            val newNFDTextItem = NFDText(0, it.title, it.date, it.content, it.type)
            nfdTextDao.insert(newNFDTextItem)
            newList.add(newNFDTextItem)
        }
    }
    return newList
  }
}