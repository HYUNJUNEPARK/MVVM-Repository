dataBinding</br>

```
<layout xmlns:android=...>
    <data>
        <variable
            name="viewModel"
            type="com.june.simplecounter.MainViewModel" />
    </data>
</layout>
```


LiveData/ViewModel</br>

```
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = model
    }
}

class MainViewModel() : ViewModel() { //ViewModel
    var count = MutableLiveData<Int>() //LiveData

    init {
        count.value = 0
    }

    fun increase() {
        count.value = count.value?.plus(1)
    }

    fun decrease() {
        count.value = count.value?.minus(1)
    }
}
```