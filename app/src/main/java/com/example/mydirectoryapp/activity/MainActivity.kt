package com.example.mydirectoryapp.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
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
import com.example.mydirectoryapp.model.Contact
import com.example.mydirectoryapp.permission.Permission

class MainActivity: AppCompatActivity() {
    companion object {
        //TODO 연락처, 키패드에서 공동으로 쓰일 리스트
        var contactListAll = mutableListOf<Contact>()
    }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var resultListener: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        Permission(this).checkPermissions()

        initFragments()
        initLinkBottomNaviWithViewPager()
        initDrawerToggle()
        initDrawerNavigationItemListener()
        initResultListener()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.all { it ==  PackageManager.PERMISSION_GRANTED}) {
            Permission(this).permissionGranted()
        }
        else {
            Permission(this).permissionDenied()
        }
    }


//menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        menu?.apply {
            findItem(R.id.addPerson).isVisible = (binding.viewPager.currentItem == 0)
            findItem(R.id.filterRecentCalls).isVisible = (binding.viewPager.currentItem ==3)
        }
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



//Start Functions
    private fun initResultListener() {
        resultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val adapter = binding.viewPager.adapter as FragmentAdapter
            val fragment = adapter.fragmentList[0] as ContactFragment
            val contactAdapter = fragment.binding.recyclerView.adapter as ContactAdapter
            contactListAll.clear()
            fragment.getContents()
            contactAdapter.contactList = contactListAll
            contactAdapter.notifyDataSetChanged()
        }
    }

    private fun initFragments() {
        val fragmentList = listOf(
            ContactFragment(),
            KeypadFragment(),
        )
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        binding.viewPager.adapter = adapter
    }

    private fun initLinkBottomNaviWithViewPager() {
        val toolbarTitleList = listOf(
            getString(R.string.contact),
            getString(R.string.keypad)
        )
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigationContact -> {
                    binding.viewPager.currentItem = 0
                    binding.toolBarTitle.text = toolbarTitleList[0]
                    true
                }
                R.id.navigationKeypad -> {
                    binding.viewPager.currentItem = 2
                    binding.toolBarTitle.text = toolbarTitleList[1]
                    true
                }
                else -> {
                    false
                }
            }
        }
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //TODO 확인해 볼 코드
                    invalidateOptionsMenu()

                    binding.toolBarTitle.text = toolbarTitleList[position]
                    binding.bottomNavigation.menu.getItem(position).isChecked = true
                }
            }
        )
    }

//Drawer
    private fun initDrawerToggle() {
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opend, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()
    }

    private fun initDrawerNavigationItemListener() {
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