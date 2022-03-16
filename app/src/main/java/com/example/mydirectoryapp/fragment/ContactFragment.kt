package com.example.mydirectoryapp.fragment

import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
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




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        initRecyclerView(binding)


        return binding.root
    }

    private fun initRecyclerView(binding: FragmentContactBinding) {
        val adapter = ContactAdapter(requireContext())
        adapter.contactList = getContact()
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
    }

    fun getContact(): MutableList<Contact> {
        val contactList = mutableListOf<Contact>()
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
            contactList.add(person)
        }

        //cursor?.close()
        return contactList
    }
}