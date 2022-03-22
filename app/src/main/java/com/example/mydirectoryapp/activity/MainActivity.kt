package com.example.mydirectoryapp.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.adapter.ContactAdapter
import com.example.mydirectoryapp.adapter.FragmentAdapter
import com.example.mydirectoryapp.databinding.ActivityMainBinding
import com.example.mydirectoryapp.fragment.*
import com.example.mydirectoryapp.permission.BaseActivity

class MainActivity : BaseActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var resultListener: ActivityResultLauncher<Intent>

    private val permissionList: Array<String> = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.CALL_PHONE
    )

    companion object {
        val TAG = "testLog"
        const val permissionRequestCode = 99


    }

    var hasPermission: Boolean ? = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        //TODO 코드 체크
        requirePermissions(permissionList, permissionRequestCode)
        if (hasPermission == true) {
            initFragments()
        }
        else {
            Toast.makeText(this, "권한 승인 필요", Toast.LENGTH_SHORT).show()
        }

        initLinkBottomNaviWithViewPager()
        initDrawerToggle()
        initDrawerNavigationItemListener()

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

//permission
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.all { it ==  PackageManager.PERMISSION_GRANTED}) {
            permissionGranted(requestCode)
        }
        else {
            permissionDenied(requestCode)
        }
    }

    override fun permissionGranted(requestCode: Int) {
        hasPermission = true
        initFragments()

    }

    override fun permissionDenied(requestCode: Int) {
        hasPermission = false
    }

//Start Function
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
    }

    private fun initLinkBottomNaviWithViewPager() {
        val toolbarTitleList = listOf(
            getString(R.string.contact),
            getString(R.string.message),
            getString(R.string.keypad),
            getString(R.string.recent),
            getString(R.string.calendar)
        )
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigationContact -> {
                    binding.viewPager.currentItem = 0
                    binding.toolBarTitle.text = toolbarTitleList[0]
                    true
                }
                R.id.navigationMessage -> {
                    binding.viewPager.currentItem = 1
                    binding.toolBarTitle.text = toolbarTitleList[1]
                    true
                }
                R.id.navigationKeypad -> {
                    binding.viewPager.currentItem = 2
                    binding.toolBarTitle.text = toolbarTitleList[2]
                    true
                }
                R.id.navigationRecent -> {
                    binding.viewPager.currentItem = 3
                    binding.toolBarTitle.text = toolbarTitleList[3]
                    true
                }
                R.id.navigationCalendar -> {
                    binding.viewPager.currentItem = 4
                    binding.toolBarTitle.text = toolbarTitleList[4]
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