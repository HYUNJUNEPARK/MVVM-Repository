package com.example.mydirectoryapp.fragment

import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydirectoryapp.adapter.ContactAdapter
import com.example.mydirectoryapp.databinding.FragmentContactBinding
import com.example.mydirectoryapp.model.Contact

class ContactFragment : Fragment() {

    var _binding: FragmentContactBinding? = null
    val binding get() = _binding!!
    var contactListAll = mutableListOf<Contact>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        contactListAll.clear()
        getContact()

        initRecyclerView(binding, contactListAll)
        initButtons()

        return binding.root
    }

    private fun initButtons() {
        binding.simBButton.setOnClickListener {
            val contactListSimB = mutableListOf<Contact>()
            for (i in contactListAll) {
                if (i.sim == "1") {
                    contactListSimB.add(i)
                }
            }
            initRecyclerView(binding, contactListSimB)
        }

        binding.simAButton.setOnClickListener {
            val contactListSimA = mutableListOf<Contact>()
            for (i in contactListAll) {
                if (i.sim == "2") {
                    contactListSimA.add(i)
                }
            }
            initRecyclerView(binding, contactListSimA)
        }

        binding.allButton.setOnClickListener {
            initRecyclerView(binding, contactListAll)
        }

        binding.simElseButton.setOnClickListener {
            val contactListSimElse = mutableListOf<Contact>()
            for (i in contactListAll) {
                if (i.sim != "1" && i.sim != "2") {
                    contactListSimElse.add(i)
                }
            }
            initRecyclerView(binding, contactListSimElse)
        }
    }

    //binding 파라미터로 안넣어줘도 될듯함
    private fun initRecyclerView(binding: FragmentContactBinding, contactList: MutableList<Contact>) {
        val adapter = ContactAdapter(requireContext())
        adapter.contactList = contactList
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
    }

    fun getContact() {
        val uri : Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val contactArray = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE /*1)HOME, 2)MOBILE, 3)WORK*/
        )
        val cursor = context?.contentResolver?.query(uri, contactArray, null, null, null)
        while (cursor?.moveToNext() == true) {
            val name = cursor.getString(0)
            val number = cursor.getString(1)
            val sim = cursor.getString(2)
            val person= Contact(name, number, sim)
            contactListAll.add(person)
        }
        cursor?.close()
    }
}