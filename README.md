
(1) ViewModelProvider 를 이용한 초기화
-ViewModelProvider 의 파라미터로 MainActivity(View)를 전달하고 그로부터 ViewModel Class 를 넣어 ViewModel 초기화
(MainActivity 의 라이프 사이클을 따르게 됨)

`val viewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)`





(2) by viewModels() 를 이용한 초기화
-아래 build.gradle 세팅이 끝나면 Activity, Fragment 에 대한 viewModels 확장함수를 이용해 viewModel 을 생성할 수 있음
cf. viewModels 확장 함수 : ComponentActivity.viewModels(), Fragment.viewModels()
-by viewModels() 를 이용한 초기화는 해당 viewModel 이 초기화되는 Activity, Fragment 에 종속됨

```
dependencies {
    implementation 'androidx.activity:activity-ktx:1.2.2'
    implementation 'androidx.fragment:fragment-ktx:1.3.3'
}
```

`val viewModel: MainViewModel by viewModels()`







(3)by activityViewModels() 를 이용한 초기화
-Fragment 에서만 사용 가능한 viewModel 초기화 방식
-Fragment 에 대한 확장 함수로 짜여있어 Activity 에서 사용 불가
-Fragment 가 종속된 Activity 의 Lifecycle 에 ViewModel 을 종속 시킴
-Activity(ViewModelStoreOwner)를 공유하는 Fragment 간의 데이터 전달이 가능해짐 //TODO 추가 공부 필요

`private val sharedViewModel: FragmentAViewModel by activityViewModels()`




/////

ViewModel 의 다양한 초기화 방법
https://kotlinworld.com/89


