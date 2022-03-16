package com.example.mydirectoryapp.activity

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.adapter.ContactAdapter
import com.example.mydirectoryapp.adapter.FragmentAdapter
import com.example.mydirectoryapp.databinding.ActivityMainBinding
import com.example.mydirectoryapp.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var resultListener: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        initFragments()
        initDrawerToggle()
        initNavigationItemListener()

        //[START 공부]
        resultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val adapter = binding.viewPager.adapter as FragmentAdapter
            val fragment = adapter.fragmentList[0] as ContactFragment
            val contactAdapter = fragment.binding.recyclerView.adapter as ContactAdapter
            fragment.contactListAll.clear()
            fragment.getContact()
            contactAdapter.contactList = fragment.contactListAll
            contactAdapter.notifyDataSetChanged()

        }
        //[END 공부]
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navigationContact -> {
                binding.viewPager.currentItem = 0
                binding.toolBarTitle.text = getString(R.string.contact)
                return true
            }
            R.id.navigationMessage -> {
                binding.viewPager.currentItem = 1
                binding.toolBarTitle.text = getString(R.string.message)
                return true
            }
            R.id.navigationKeypad -> {
                binding.viewPager.currentItem = 2
                binding.toolBarTitle.text = getString(R.string.keypad)
                return true
            }
            R.id.navigationRecent -> {
                binding.viewPager.currentItem = 3
                binding.toolBarTitle.text = getString(R.string.recent)
                return true
            }
            R.id.navigationCalendar -> {
                binding.viewPager.currentItem = 4
                binding.toolBarTitle.text = getString(R.string.calendar)
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
        //Toolbar Menu Event
        when(item.itemId) {
            R.id.menu1 -> Toast.makeText(this, getString(R.string.test_message), Toast.LENGTH_SHORT).show()
            R.id.addPerson -> {
                var intent = Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI)
                resultListener.launch(intent)
            }
        }
        //DrawerLayout-NavigationView Event
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initFragments() {
        val fragmentList = listOf(
            ContactFragment(),
            MessageFragment(),
            KeypadFragment(),
            RecentFragment(),
            CalendarFragment()
        )
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


    private fun initDrawerToggle() {
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opend, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()
    }

    private fun initNavigationItemListener() {
        binding.mainDrawerView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.item1 -> Toast.makeText(this, "Item1 Clicked", Toast.LENGTH_SHORT).show()
                R.id.item2 -> Toast.makeText(this, "Item2 Clicked", Toast.LENGTH_SHORT).show()
                R.id.item3 -> Toast.makeText(this, "Item3 Clicked", Toast.LENGTH_SHORT).show()
                R.id.item4 -> Toast.makeText(this, "Item4 Clicked", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}