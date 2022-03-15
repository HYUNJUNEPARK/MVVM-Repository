package com.example.mydirectoryapp.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.adapter.FragmentAdapter
import com.example.mydirectoryapp.databinding.ActivityMainBinding
import com.example.mydirectoryapp.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initFragments()
    }

    private fun initFragments() {
        val fragmentList = listOf(ContactFragment(), MessageFragment(), KeypadFragment(), RecentFragment(), CalendarFragment())
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNavigation.menu.getItem(position).isChecked = true
                }
            }
        )
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navigationContact -> {
                binding.viewPager.currentItem = 0
                return true
            }
            R.id.navigationMessage -> {
                binding.viewPager.currentItem = 1
                return true
            }
            R.id.navigationKeypad -> {
                binding.viewPager.currentItem = 2
                return true
            }
            R.id.navigationRecent -> {
                binding.viewPager.currentItem = 3
                return true
            }
            R.id.navigationCalendar -> {
                binding.viewPager.currentItem = 4
                return true
            }
            else -> {
                return false
            }
        }
    }
}