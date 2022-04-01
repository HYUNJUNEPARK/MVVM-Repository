<img src="https://github.com/HYUNJUNEPARK/ImageRepository/blob/master/androidProgramming/mvvm1.png"  width="200" height="400"/>
<img src="https://github.com/HYUNJUNEPARK/ImageRepository/blob/master/androidProgramming/mvvm2.png"  width="200" height="400"/>
---

**(1) ViewModelProvider 를 이용한 초기화**</br>
-ViewModelProvider 의 파라미터로 MainActivity(View)를 전달하고 그로부터 ViewModel Class 를 넣어 ViewModel 초기화</br>
(MainActivity 의 라이프 사이클을 따르게 됨)</br>

`val viewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)`</br>

<br></br>
**(2) by viewModels() 를 이용한 초기화**</br>
-아래 build.gradle 세팅이 끝나면 Activity, Fragment 에 대한 viewModels 확장함수를 이용해 viewModel 을 생성할 수 있음</br>
cf. viewModels 확장 함수 : ComponentActivity.viewModels(), Fragment.viewModels()</br>
-by viewModels() 를 이용한 초기화는 해당 viewModel 이 초기화되는 Activity, Fragment 에 종속됨</br>

```
dependencies {
    implementation 'androidx.activity:activity-ktx:1.2.2'
    implementation 'androidx.fragment:fragment-ktx:1.3.3'
}
```

`val viewModel: MainViewModel by viewModels()`</br>
<br></br>

**(3)by activityViewModels() 를 이용한 초기화**</br>
-Fragment 에서만 사용 가능한 viewModel 초기화 방식</br>
-Fragment 에 대한 확장 함수로 짜여있어 Activity 에서 사용 불가</br>
-Fragment 가 종속된 Activity 의 Lifecycle 에 ViewModel 을 종속 시킴</br>
-Activity(ViewModelStoreOwner)를 공유하는 Fragment 간의 데이터 전달이 가능해짐 //TODO 추가 공부 필요</br>

`private val sharedViewModel: FragmentAViewModel by activityViewModels()`</br>


---


ViewModel 의 다양한 초기화 방법
https://kotlinworld.com/89


