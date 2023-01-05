package com.study.mvvmbasicex.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.study.mvvmbasicex.R
import com.study.mvvmbasicex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val countViewModel: CountViewModel by viewModels()
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.countViewModel = countViewModel
        binding.userInfoViewModel = userInfoViewModel

        userInfoViewModel.isUserInfoFetching.observe(this){ isFetching ->
            if (isFetching) {
                binding.progressBar.visibility = View.VISIBLE
            }
            else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }
}