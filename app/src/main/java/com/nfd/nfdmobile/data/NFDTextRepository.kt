package com.nfd.nfdmobile.data

class NFDTextRepository private constructor(
    private val database: AppDatabase,
    private val service: ContentAPIService,
    private val context: Context
) {

    // fun getArticles() {
    //     val type = "article"
    //     val nfdTextDao = database.nfdTextDao()
    //     val existingTexts = nfdTextDao.getTextsByType(type)

    //     service.getArticles().enqueue(object : Callback<NFDArticlesData> {
    //         override fun onResponse(call: Call<NFDArticlesData>, response: Response<NFDArticlesData>) {
    //             if (response.code() == 200) {
    //                 val articlesResponse = response.body()!!
    //                 val retrievedList = body.data?.articles
    //                 val sortedList = populateDatabase(nfdTextDAO, existingTexts, retrievedList, type)
    //                 return sortedList
    //             }
    //             return existingTexts
    //         }
    //         override fun onFailure(call: Call<NFDArticlesData>, t: Throwable) {
    //           return existingTexts
    //         }
    //     })
    // }

    // fun getPractices() {
    //     val type = "practice"
    //     val nfdTextDao = database.nfdTextDao()
    //     val existingTexts = nfdTextDao.getTextsByType(type)

    //     service.getPractices().enqueue(object : Callback<NFDPracticesData> {
    //         override fun onResponse(call: Call<NFDPracticesData>, response: Response<NFDPracticesData>) {
    //             if (response.code() == 200) {
    //                 val practicesResponse = response.body()!!
    //                 val retrievedList = body.data?.practices
    //                 val sortedList = populateDatabase(nfdTextDAO, existingTexts, retrievedList, type)
    //                 return sortedList
    //             }
    //             return existingTexts
    //         }
    //         override fun onFailure(call: Call<NFDPracticesData>, t: Throwable) {
    //           return existingTexts
    //         }
    //     })
    // }

    // private fun populateDatabase(nfdTextDao: NFDTextDao, existingTexts: List<NFDText>, retrievedList: List<NFDText>, type: String) {
    //     var newList: List<NFDText> = existingTexts
        
    //     if (existingTexts.size === 0) {
    //         // NOTE: I'm not sure if these need to be reversed.
    //         return retrievedList.reversed().forEach {
    //             nfdTextDAO.insert(NFDText(0, it.title, it.date, it.content, it.type))
    //         }
    //     }
    //     for (retrievedText in retrievedList) {
    //         val doesTextExist = existingTexts.find {
    //             it.title == retrievedText.title
    //         }
    //         !doesTextExist?.let {
    //             newNFDTextItem = NFDText(0, it.title, it.date, it.content, it.type)
    //             nfdTextDAO.insert(newNFDTextItem)
    //             newList.add(newNFDTextItem)
    //         }
    //     }
    //     return newList
    // }
}