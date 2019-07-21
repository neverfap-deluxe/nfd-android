package com.nfd.nfdmobile.nfdtext

import android.content.Context
import android.util.Log
import android.widget.ListView
import com.nfd.nfdmobile.services.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NFDText(
    val title: String?,
    val date: String?,
    val content: String?) {

    companion object {
        lateinit var articlesResponse: NFDArticlesData
        lateinit var practicesResponse: NFDPracticesData

        fun getItemsFromContentAPI(type: String, view: ListView, context: Context) {
            val service = ContentAPIService.create()

            // NOTE: Call Content API
            when (type) {
                "articles" -> {
                    val call = service.getArticles()
                    Log.d("REQUEST", call.toString() + "")
                    call.enqueue(object : Callback<NFDArticlesData> {
                        override fun onResponse(call: Call<NFDArticlesData>, response: Response<NFDArticlesData>) {
                            if (response.code() == 200) {
                                articlesResponse = response.body()!!

                                val retrievedList = convertArticlesListToNFDText(articlesResponse)
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
                                practicesResponse = response.body()!!
                                val retrievedList = convertPracticesListToNFDText(practicesResponse)
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
                    nfdTextList.add(NFDText(item.title, item.date, item.content))
                }
            }

            return nfdTextList
        }

        private fun convertPracticesListToNFDText(body: NFDPracticesData): ArrayList<NFDText> {
            var nfdTextList = ArrayList<NFDText>()
            val practices = body.data?.practices

            practices?.let {
                for (item in practices.take(5)) {
                    nfdTextList.add(NFDText(item.title, item.date, item.content))
                }
            }
            return nfdTextList
        }

    }

}