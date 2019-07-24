package com.nfd.nfdmobile.data

class NFDTextRepository private constructor(
    private val nfdTextDao: NFDTextDao,
    private val database: AppDatabase,
    private val service: ContentAPIService,
    private val context: Context
) {
    fun getArticles() {
        val existingTexts = nfdTextDao.getTextsByType("article")
        val call = service.getArticles()
        val completeList = retrieveFromDatabaseNfdText(database, type)

        call.enqueue(object : Callback<NFDArticlesData> {
            override fun onResponse(call: Call<NFDArticlesData>, response: Response<NFDArticlesData>) {
                if (response.code() == 200) {
                    val articlesResponse = response.body()!!
                    val retrievedList = body.data?.articles

                    populateDatabase(database, retrievedList, "article")

                    NFDTextAdapter.setupAdapterAndOnClickListener(completeList, view, context, type)
                }
            }
            override fun onFailure(call: Call<NFDArticlesData>, t: Throwable) {
                NFDTextAdapter.setupAdapterAndOnClickListener(completeList, view, context, type)
            }
        })
    }

    fun getPractices() {
        val existingTexts = nfdTextDao.getTextsByType("practice")
        val call = service.getPractices()
        val completeList = retrieveFromDatabaseNfdText(database, type)

        call.enqueue(object : Callback<NFDPracticesData> {
            override fun onResponse(call: Call<NFDPracticesData>, response: Response<NFDPracticesData>) {
                if (response.code() == 200) {
                    val practicesResponse = response.body()!!
                    val retrievedList = body.data?.practices

                    // NOTE: Populate Database
                    populateDatabase(database, retrievedList, "practice")
                    NFDTextAdapter.setupAdapterAndOnClickListener(completeList, view, context, type)
                }
            }
            override fun onFailure(call: Call<NFDPracticesData>, t: Throwable) {
                NFDTextAdapter.setupAdapterAndOnClickListener(completeList, view, context, type)
            }
        })
    }


    private fun populateDatabase(database: AppDatabase, retrievedList: ArrayList<NFDText>, type: String) {
        val nfdTextDAO = database.NFDTextDao()

        // Ensure this is in latest order.
        val databaseTexts = nfdTextDAO.getTextsByType(type);

        if (databaseTexts.size === 0) {
            retrievedList.reversed().forEach {
                nfdTextDAO.insert(createTextItem(it.title, it.date, it.content, it.type))
            }
        } else {
            for (retrievedText in retrievedList) {
                val doesTextExist = databaseTexts.find {
                    it.title == retrievedText.title
                }
                doesTextExist?.let {
                    nfdTextDAO.insert(createTextItem(it.title, it.date, it.content, it.type))
                }
            }
        }
    }

    private fun retrieveFromDatabaseNfdText(database: AppDatabase, type: String): ArrayList<NFDText> {
        val nfdTextDAO = database.NFDTextDao()
        val completeList = nfdTextDAO.getTextsByType(type) as ArrayList<NFDTextEntity>
        return entityToText(completeList)
    }

    private fun createTextItem(title: String?, date: String?, content: String?, type: String?): NFDTextEntity {
        return NFDTextEntity(0, title, date, content, type)
    }

}