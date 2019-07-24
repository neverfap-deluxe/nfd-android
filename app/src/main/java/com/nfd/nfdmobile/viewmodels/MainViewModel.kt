
class MainViewModel(
  private val nFDTextRepository: NFDTextRepository,
) : ViewModel() {

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
      return nFDTextRepository.getArticles()
  }

  private fun loadPractices() {
      return nFDTextRepository.getPractices()
  }
}