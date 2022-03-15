package com.example.mydirectoryapp.fragment

import android.os.Bundle
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentContactBinding.inflate(inflater, container, false)

        initRecyclerView(binding)

        return binding.root
    }

    private fun initRecyclerView(binding: FragmentContactBinding) {
        val adapter = ContactAdapter()
        adapter.contactList = loadData()
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun loadData(): MutableList<Contact> {
        val contact: MutableList<Contact> = mutableListOf()
        for(i in 1..30) {
            val name = "Person $i"
            val number = "010-1234-00%02d".format(i)
            var info = Contact(name, number)
            contact.add(info)
        }
        return contact
    }
}