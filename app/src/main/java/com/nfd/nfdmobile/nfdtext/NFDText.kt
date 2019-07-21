package com.nfd.nfdmobile.nfdtext

import android.content.Context
import android.util.Log
import android.widget.ListView
import androidx.room.Room
import com.nfd.nfdmobile.room.AppDatabase
import com.nfd.nfdmobile.services.ContentAPIService
import com.nfd.nfdmobile.services.NFDArticlesData
import com.nfd.nfdmobile.services.NFDPracticesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.ClipData.Item
import com.nfd.nfdmobile.room.TextEntity
import android.content.ClipData.Item




class NFDText(
    val title: String?,
    val date: String?,
    val content: String?) {

    companion object {
        fun getItemsFromContentAPI(type: String, view: ListView, context: Context) {

            val database = Room.databaseBuilder(context, AppDatabase::class.java, "nfd_db")
                .allowMainThreadQueries()
                .build()

            val service = ContentAPIService.create()

            // NOTE: Call Content API
            when (type) {
                "articles" -> {
                    val call = service.getArticles()
                    Log.d("REQUEST", call.toString() + "")
                    call.enqueue(object : Callback<NFDArticlesData> {
                        override fun onResponse(call: Call<NFDArticlesData>, response: Response<NFDArticlesData>) {
                            if (response.code() == 200) {
                                val articlesResponse = response.body()!!
                                val retrievedList = convertArticlesListToNFDText(articlesResponse)

                                // NOTE: Populate Database
                                populateDatabase(database, retrievedList, "article")

                                setupAdapterAndOnClickListener(retrievedList, view, context, type)
                            }
                        }
                        override fun onFailure(call: Call<NFDArticlesData>, t: Throwable) {
                            throw Exception("NFDArticlesData: Could not retrieve. $t.toString()")
                        }
                    })

                }
                "practices" -> {
                    val call = service.getPractices()
                    call.enqueue(object : Callback<NFDPracticesData> {
                        override fun onResponse(call: Call<NFDPracticesData>, response: Response<NFDPracticesData>) {
                            if (response.code() == 200) {
                                val practicesResponse = response.body()!!
                                val retrievedList = convertPracticesListToNFDText(practicesResponse)

                                // NOTE: Populate Database
                                populateDatabase(database, retrievedList, "practice")

                                setupAdapterAndOnClickListener(retrievedList, view, context, type)
                            }
                        }
                        override fun onFailure(call: Call<NFDPracticesData>, t: Throwable) {
                            throw Exception("NFDPracticesData: Could not retrieve. $t.toString()")
                        }
                    })
                }
                else -> {
                    throw Exception("contactContentAPI $type unknown")
                }
            }
        }

        private fun populateDatabase(database: AppDatabase, retrievedList: ArrayList<NFDText>, type: String) {
            val textDAO = database.getTextDAO()

            // get all items from the database
            val databaseTexts = textDAO.getTextsWithType();

            for (retrievedNFDText in retrievedList) {

            }

            textDAO.insert(newText)
        }

        private fun createTextItem() {
            val newText = TextEntity()

            newText.setName("Item001")
            newText.setDate("Item 001")
            newText.setContent(1000)
            newText.setType(1000)

            return newText
        }

        private fun setupAdapterAndOnClickListener(retrievedList: ArrayList<NFDText>, view: ListView, context: Context, type: String) {
            // NOTE: Populates Text View
            val adapter = NFDTextAdapter(context, retrievedList)
            view.adapter = adapter

            // NOTE: Populates Text OnClick
            view.setOnItemClickListener { _, _, position, _ ->
                val selectedTextItem = retrievedList[position]
                val textItemIntent = NFDTextActivity.newIntent(context, selectedTextItem, type)

                context.startActivity(textItemIntent)
            }
        }

        private fun convertArticlesListToNFDText(body: NFDArticlesData): ArrayList<NFDText> {
            var nfdTextList = ArrayList<NFDText>()
            val articles = body.data?.articles

            articles?.let {
                for (item in articles.take(5)) {
                    nfdTextList.add(NFDText(item.title, item.date, item.content, "article"))
                }
            }

            return nfdTextList
        }

        private fun convertPracticesListToNFDText(body: NFDPracticesData): ArrayList<NFDText> {
            var nfdTextList = ArrayList<NFDText>()
            val practices = body.data?.practices

            practices?.let {
                for (item in practices.take(5)) {
                    nfdTextList.add(NFDText(item.title, item.date, item.content, "practice"))
                }
            }
            return nfdTextList
        }

    }

}