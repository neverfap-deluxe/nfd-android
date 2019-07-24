
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

  private fun loadArticles() {
      val type = "article"
      val database = AppDatabase.getInstance(context)
      val nfdTextDao = database.nfdTextDao()
      val existingTexts = nfdTextDao.getTextsByType(type)

      val service = ContentServiceAPI.create()

      service.getArticles().enqueue(object : Callback<NFDArticlesData> {
          override fun onResponse(call: Call<NFDArticlesData>, response: Response<NFDArticlesData>) {
              if (response.code() == 200) {
                  val articlesResponse = response.body()!!
                  val retrievedList = body.data?.articles
                  val sortedList = populateDatabase(nfdTextDAO, existingTexts, retrievedList, type)
                  articles.setValue(sortedList)
              }
              articles.setValue(existingTexts)
          }
          override fun onFailure(call: Call<NFDArticlesData>, t: Throwable) {
            articles.setValue(existingTexts)
          }
      })
  }

  private fun loadPractices() {
      val type = "practice"
      val database = AppDatabase.getInstance(context)
      val nfdTextDao = database.nfdTextDao()
      val existingTexts = nfdTextDao.getTextsByType(type)

      val service = ContentServiceAPI.create()

      service.getPractices().enqueue(object : Callback<NFDPracticesData> {
          override fun onResponse(call: Call<NFDPracticesData>, response: Response<NFDPracticesData>) {
              if (response.code() == 200) {
                  val practicesResponse = response.body()!!
                  val retrievedList = body.data?.practices
                  val sortedList = populateDatabase(nfdTextDAO, existingTexts, retrievedList, type)
                  practices.setValue(sortedList)
              }
              practices.setValue(existingTexts)
          }
          override fun onFailure(call: Call<NFDPracticesData>, t: Throwable) {
            practices.setValue(existingTexts)
          }
      })
  }

  private fun populateDatabase(nfdTextDao: NFDTextDao, existingTexts: List<NFDText>, retrievedList: List<NFDText>, type: String) {
    var newList: List<NFDText> = existingTexts
    
    if (existingTexts.size === 0) {
        // NOTE: I'm not sure if these need to be reversed.
        return retrievedList.reversed().forEach {
            nfdTextDAO.insert(NFDText(0, it.title, it.date, it.content, it.type))
        }
    }
    for (retrievedText in retrievedList) {
        val doesTextExist = existingTexts.find {
            it.title == retrievedText.title
        }
        !doesTextExist?.let {
            newNFDTextItem = NFDText(0, it.title, it.date, it.content, it.type)
            nfdTextDAO.insert(newNFDTextItem)
            newList.add(newNFDTextItem)
        }
    }
    return newList
  }
}