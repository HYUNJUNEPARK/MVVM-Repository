package com.example.mydirectoryapp.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
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
        setSupportActionBar(binding.toolBar)

        initFragments()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu1 -> Toast.makeText(this, "문의하기 버튼 클릭됨", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
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
}