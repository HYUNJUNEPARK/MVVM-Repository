https://developer.android.com/jetpack/guide?hl=ko

---

**1.UI Layer**</br>
-어플리케이션 데이터 변경사항을 UI가 표시할 수 있는 형식으로 변한 후 표시하는 파이프라인</br>
-UI는 데이터 레이어에서 가져온 어플리케이션 상태를 시작적으로 나타내는 것을 의미한다.</br>

1.1 UI State</br>
-UI 렌더링에 필요한 세부정보가 포함된 변경 불가능한(불변성) 스냅샷.</br>
즉, 앱에서 사용자에게 표시하는 정보를 UI State 라고하며, UI 에서 UI State 를 직접 수정해서는 안된다.</br>
이 원칙을 위반하면 동일한 정보가 여러 정보 소스(Source of truth)에서 비롯되어 데이터 불일치와 같은 미세한 버그가 발생한다.</br>

1.2 State Holder</br>
-UI State 를 생성하는 역할을 담당하고 생성 작업에 필요한 로직을 포함하는 클래스</br>
-일반적인 구현은 ViewModel 의 인스턴스지만 어플리케이션의 요구사항에 따라 간단한 클래스로도 구현하기도 한다.</br>

1.2.1 ViewModel</br>
-데이터 레이어에 엑세스할 권한이 있는 화면 수준 UI State 를 관리하는 데 권장되는 구현</br>
-앱의 이벤트에 적용할 로직을 정의하고 결과로 업데이트되는 UI State 를 생성한다.</br>
-LiveData 또는 StateFlow 와 같이 관찰 가능한 데이터 홀더에 UI 상태를 노출해 ViewModel 에서 직접 데이터를 가져오지 않고도 UI가 상태 변경사항에 반응할 수 있다.</br>

1.3 UDF(Unidirectional Data Flow, 단방향 데이터 흐름)</br>
-UI 레이어 내 UI 요소에서 이벤트가 ViewModel 로 향하고, ViewModel 에서 UI State 가 UI 요소로 향하는 패턴</br>

1.3.1 UDF 시나리오</br>
(1)ViewModel 에서 DataLayer 내부 Current app data 를 가져와 Current UI state 를 생성 후 UI 요소에 반영한다.</br>
(2)UI 요소에서 이벤트가 발생하면 ViewModel 은 이벤트에 따른 state 변경(data 변경)을 DataLayer 에 알린다.</br>
(3)DataLayer 는 data 변경을 반영한다. (이후 (1)과정부터 반복)</br>

1.3.2 UDF 를 사용하는 이유</br>
-데이터 일관성 : UI 용 정보 소스가 하나다.</br>
-테스트 가능성 : 상태 소스가 분리되므로 UI와 별개로 테스트할 수 있다.</br>
-유지 관리성 : 상태 변경은 잘 정의된 패턴을 따른다. 즉 변경은 사용자 이벤트 및 데이터를 가져온 소스 모두의 영향을 받는다.</br>

```kotlin
//UILayer Example
class NewsViewModel(
    private val repository: NewsRepository,
    //...
) : ViewModel() {
    //UiState 스트림을 만드는 일반적인 방법 : ViewModel 에서 지원되는 변경 가능한 스트림을 변경 불가능한 스트림으로 노출
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(NewsUiState())
    private var fetchJob: Job? = null
 
    //비동기 작업을 실행해야하는 경우 viewModelScope를 사용하여 코루틴을 실행하고 코루틴이 완료되면 변경 가능한 상태를 업데이트할 수 있다.
    fun fetchArticles(category: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val newsItems = repository.newsItemsForCategory(category)
                _uiState.update {
                    it.copy(newsItems = newsItems)
                }
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                _uiState.update {
                    val messages = getMessagesFromThrowable(ioe)
                    it.copy(userMessages = messages)
                 }
            }
        }
    }
}

//UiState 객체의 스트림을 사용하려면 사용 중인 관찰 가능한 데이터 유형(LiveData, StateFlow)의 터미널 연산자를 사용한다. - LiveData의 경우 observe(), StateFlow의 경우 collect()
class NewsActivity : AppCompatActivity() {
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //...
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // Update UI elements
                }
            }
        }
    }
}
```

---

**2.Data Layer**</br>
-데이터를 불러오는 출처인 DataSource 와 여러 개의 DataSource 를 각각 포함할 수 있는 Repository 로 구성된다.</br>
-데이터 레이어에서 노출된 데이터는 변경 불가능해야하며, 그래야 데이터를 일관되게 유지할 수 있다.</br>

2.1 DataSource</br>
-데이터 작업을 위해 애플리케이션과 시스템간의 중간다리 역할을 하는 클래스</br>
-DataSource 클래스는 파일, 네트워크 소스, 로컬 DB 등 같은 하나의 데이터 소스만 사용해야한다.</br>
즉, 동일한 DataSource 를 네트워크에서도 불러오고 데이터 DB 에서도 불러오면 안된다.</br>
-데이터 소스를 사용하는 클래스는 데이터가 저장되는 방식(ex. LocalDB, File 등)을 알 수 없다.</br>
즉, 구현 세부정보에 따라 데이터 소스의 이름을 지정하지 않는다. ex. UserSharedPreferenceDataSource</br>

2.2 Repository</br>
-DataSource 의 진입점으로 사용되는 클래스</br>
즉, ViewModel 에서 데이터를 가져올 때 Repository 를 통해 가져오게 된다.</br>
서로 다른 DataSource 를 결합하고 DataSource 간의 잠재적인 충돌을 해결하여 정기적 또는 사용자 이벤트에 따라 *정보 소스(Source of truth) 업데이트한다.</br>
-계층 구조의 다른 레이어는 DataSource 에 직접 액세스해서는 안되며, 데이터 영역의 진입점은 항상 Repository 클래스여야한다.</br>
-하나의 Repository 는 여러개의 Repository 를 가질 수 있으며, 계층 구조의 최하위 Repository 는 DataSource 를 갖고 있다.</br>

```kotlin
//서로 다른 DataSource 를 결합한 Repository 예시
class ExampleRepository(
    private val exampleRemoteDataSource: ExampleRemoteDataSource, // network
    private val exampleLocalDataSource: ExampleLocalDataSource // database
) { /* ... */ }
```

cf. 정보 소스(Source of truth)</br>
-각 Repository 는 하나의 정보 소스를 정의하는 것이 중요하며, 항상 일관되고 정확하며 최신 상태인 데이터를 포함한다.</br>
-정보 소스는 DataSource(ex. Local DB) 또는 Repository 에 포함될 수 있는 메모리 내 캐시일 수도 있다.</br>
-Repository 마다 정보 소스가 다를 수 있다.</br>
ex. LoginRepository 클래스는 캐시를 정보 소스로 사용하고, PaymentsRepository 클래스는 네트워크 DataSource 를 사용하는 경우</br>

2.2.1 Repository 클래스에서 담당하는 작업</br>
-데이터 변경사항을 한 곳에 집중</br>
-여러 데이터 소스 간의 충돌 해결</br>
-비지니스 로직 포함</br>
-앱에 데이터 노출</br>
-앱의 나머지 부분에서 데이터 소스 추상화</br>

2.3 데이터 저장 및 디스크에서 가져오기</br>
-작업 중인 데이터가 프로세스 중단 후에도 유지되어야 하는 경우 데이터의 특징에 따라 다른 방식으로 디스크에 저장한다.</br>

2.3.1 RoomDB</br>
-쿼리해야하거나 참조 무결성이 필요하거나 부분 업데이트가 필요한 대규모 데이터 세트의 경우</br>
ex. 뉴스 기사나 작성자를 DB 에 저장하는 경우</br>
https://developer.android.com/training/data-storage/room?hl=ko</br>

2.3.2 DataStore</br>
-쿼리하거나 부분적으로 업데이트하지 않고 검색 및 설정해야하는 소규모 데이터 세트의 경우</br>
-사용자 설정과 같은 키-값 쌍을 저장하는 경우 적합하다.</br>
ex. 사용자의 기본 날짜 형식, 기타 표시 환경 설정 등의 데이터를 저장하는 경우</br>
https://developer.android.com/topic/libraries/architecture/datastore?hl=ko#proto-create</br>

2.3.3 File</br>
-JSON 객체, 비트맵 객체 같은 데이터 청크의 경우</br>
-File 객체로 작업하고 스레드 전환을 처리해야한다.</br>

2.4 WorkManager 를 사용하여 작업 예약</br>
-신뢰할수 있는 비동기 작업을 쉽게 예약할 수 있으며 제약 조건을 관히할 수 있는 영구 작업에 권장되는 라이브러리</br>
-대부분의 백그라운드 처리는 지속적인 작업을 통해 가장 잘 처리되므로 백그라운드 처리에 권장하는 기본 API</br>
https://developer.android.com/topic/libraries/architecture/workmanager?hl=ko</br>

---

**3. Domain Layer**</br>
-UI 레이어(일반적으로 ViewModel)와 데이터 레이어 사이에 있는 선택적 레이어로, '복잡성을 처리'하거나 '재사용성'을 선호하는 등 필요한 경우에만 사용한다.</br>
-복잡한 비지니스 로직, 또는 여러 ViewModel 에서 재사용되는 간단한 비즈니스 로직의 캡슐화를 담당한다.</br>
-도메인 레이어 클래스를 일반적으로 'use case' 또는 'interactor' 라고 하며, 간단하고 가볍게 유지하려면 각 유즈 케이스는 하나의 기능을 담당해야하고 변경 가능한 데이터를 포함해서는 안된다.</br>
ex. ViewModel 에서 시간대를 사용하여 화면에 적잘한 메시지를 표시하는 경우 GetTimeZoneUseCase 클래스라고 명명</br>
-유즈 케이스는 일반적으로 Repository 클래스에 종속되며, Repository 와 동일한 방법(자바: 콜백, 코틀린: 코루틴)을 사용하여 UI 레이어와 통신한다.</br>

3.1 Domain Layer 이점</br>
-코드 중복 방지</br>
-도메인 레이어 클래스를 사용하는 클래스의 가독성 개선</br>
-앱의 테스트 가능성 높임</br>
-책임을 분할하여 대형 클래스를 방지함</br>

---




