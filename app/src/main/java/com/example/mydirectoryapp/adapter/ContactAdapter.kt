package com.example.mydirectoryapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mydirectoryapp.databinding.ItemContactBinding
import com.example.mydirectoryapp.model.Contact

class ContactAdapter(): RecyclerView.Adapter<ContactAdapter.MyHolder>() {
    var contactList = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    inner class MyHolder(private val binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.callButton.setOnClickListener {
                Toast.makeText(binding.root.context,"Calling To ${binding.numberTextView.text}", Toast.LENGTH_SHORT).show()
            }

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context,"Open Profile : ${binding.nameTextView.text}", Toast.LENGTH_SHORT).show()
            }
        }
        fun setContact(contact: Contact) {
            binding.nameTextView.text = contact.name
            binding.numberTextView.text = contact.number
        }
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        val contact = contactList[position]
        myHolder.setContact(contact)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}