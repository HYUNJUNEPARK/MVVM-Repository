---
1.UI Layer

---

2.Data Layer
-데이터를 불러오는 출처인 DataSource 와 여러 개의 DataSource 를 각각 포함할 수 있는 Repository 로 구성된다.
-데이터 레이어에서 노출된 데이터는 변경 불가능해야하며, 그래야 데이터를 일관되게 유지할 수 있다.

2.1 DataSource
-데이터 작업을 위해 애플리케이션과 시스템간의 중간다리 역할을 하는 클래스
-DataSource 클래스는 파일, 네트워크 소스, 로컬 DB 등 같은 하나의 데이터 소스만 사용해야한다.
즉, 동일한 DataSource 를 네트워크에서도 불러오고 데이터 DB 에서도 불러오면 안된다.

2.2 Repository
-DataSource 의 진입점으로 사용되는 클래스
즉, ViewModel 에서 데이터를 가져올 때 Repository 를 통해 가져오게 된다.
서로 다른 DataSource 를 결합하고 DataSource 간의 잠재적인 충돌을 해결하여 정기적 또는 사용자 이벤트에 따라 *정보 소스(Source of truth) 업데이트한다.
-계층 구조의 다른 레이어는 DataSource 에 직접 액세스해서는 안되며, 데이터 영역의 진입점은 항상 Repository 클래스여야한다.
-하나의 Repository 는 여러개의 Repository 를 가질 수 있으며, 계층 구조의 최하위 Repository 는 DataSource 를 갖고 있다.

cf. 정보 소스(Source of truth)
-각 Repository 는 하나의 정보 소스를 정의하는 것이 중요하며, 항상 일관되고 정확하며 최신 상태인 데이터를 포함한다.
-정보 소스는 DataSource(ex. Local DB) 또는 Repository 에 포함될 수 있는 메모리 내 캐시일 수도 있다.
-Repository 마다 정보 소스가 다를 수 있다.
ex. LoginRepository 클래스는 캐시를 정보 소스로 사용하고, PaymentsRepository 클래스는 네트워크 DataSource 를 사용하는 경우


```kotlin
//서로 다른 DataSource 를 결합한 Repository 예시
class ExampleRepository(
    private val exampleRemoteDataSource: ExampleRemoteDataSource, // network
    private val exampleLocalDataSource: ExampleLocalDataSource // database
) { /* ... */ }
```

2.2.1 Repository 클래스에서 담당하는 작업
-데이터 변경사항을 한 곳에 집중
-여러 데이터 소스 간의 충돌 해결
-비지니스 로직 포함
-앱에 데이터 노출
-앱의 나머지 부분에서 데이터 소스 추상화

---

3. Domain Layer


---




