
class MainViewModel(
  private val NFDTextRepository: NFDTextRepository,
) : ViewModel() {
  val articles: LiveData<List<NFDText>>
  val practices: LiveData<List<NFDText>
}