package com.nfd.nfdmobile.nfdtext

import android.content.Context
import android.widget.ListView
import androidx.room.Room
import com.nfd.nfdmobile.AppDatabase
import com.nfd.nfdmobile.services.ContentAPIService
import com.nfd.nfdmobile.services.NFDArticlesData
import com.nfd.nfdmobile.services.NFDPracticesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NFDText(
    val title: String?,
    val date: String?,
    val content: String?,
    val type: String?) {

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
                    articlesRequestCall(call, )
                }
                "practices" -> {
                    val call = service.getPractices()
                    practicesRequestCall(call)
                }
                else -> {
                    throw Exception("contactContentAPI $type unknown")
                }
            }
        }

        private fun articlesRequestCall(call: Call<NFDArticlesData>, database: AppDatabase, view: ListView, context: Context, type: String) {
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
                    val retrievedList = retrieveFromDatabaseNfdText(database)
                    setupAdapterAndOnClickListener(retrievedList, view, context, type)

                }
            })
        }

        private fun practicesRequestCall(call: Call<NFDPracticesData>, database: AppDatabase, view: ListView, context: Context, type: String) {
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

        private fun populateDatabase(database: AppDatabase, retrievedList: ArrayList<NFDText>, type: String) {
            val textDAO = database.getTextDAO()

            // Ensure this is in latest order.
            val databaseTexts = textDAO.getTextsByType(type);

            if (databaseTexts.size === 0) {
                retrievedList.reversed().forEach {
                    textDAO.insert = NFDText(it.title, it.date, it.content, it.type)
                }
            } else {
                // Essentially, check the latest. If the latest does not exist, then
                retrievedList.reversed().forEachIndexed { index, element ->
                    // it would be really smart to simply check the date of the latest articles
                    databaseTexts.find { databaseText ->
                        databaseText.title. == element.title
                    }
                }

            }
        }

        private fun retrieveFromDatabaseNfdText(database: AppDatabase, type: String) {
            val textDAO = database.getTextDAO()
            val databaseTexts = textDAO.getTextsByType("");

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