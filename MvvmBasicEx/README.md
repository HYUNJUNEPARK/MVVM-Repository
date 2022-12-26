1.UI Layer

---

2.Data Layer
-데이터를 불러오는 출처인 DataSource 와 여러 개의 DataSource 를 각각 포함할 수 있는 Repository 로 구성된다.

2.1 DataSource
-데이터 작업을 위해 애플리케이션과 시스템간의 중간다리 역할을 하는 클래스
-DataSource 클래스는 파일, 네트워크 소스, 로컬 DB 등 같은 하나의 데이터 소스만 사용해야한다.
즉, 동일한 데이터 소스를 네트워크에서도 불러오고 데이터 DB 에서도 불러오면 안된다.

2.2 Repository
-DataSource 의 진입점으로 사용되는 클래스
즉, ViewModel 에서 데이터를 가져올 때 Repository 를 통해 가져오게 된다.
-하나의 Repository 에는 여러개의 Repository 를 가질 수 있으며, 계층 구조의 최하위 Repository 는 DataSource 를 갖고 있다.

```kotlin
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








