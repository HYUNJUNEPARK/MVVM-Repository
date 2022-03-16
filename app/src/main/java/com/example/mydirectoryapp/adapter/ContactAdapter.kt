package com.example.mydirectoryapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.databinding.ItemContactBinding
import com.example.mydirectoryapp.model.Contact

class ContactAdapter(context: Context): RecyclerView.Adapter<ContactAdapter.MyHolder>() {
    var contactList = mutableListOf<Contact>()
    val context: Context = context

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
            val charRange = IntRange(0, 0)
            binding.contactTextView.text = contact.name.slice(charRange)
            setContactBallColor(binding.contactTextView)
            binding.nameTextView.text = contact.name
            binding.numberTextView.text = contact.number

            when (contact.sim) {
                "1" -> binding.simImage.background = ContextCompat.getDrawable(context, R.drawable.sim_home_1)
                "2" -> binding.simImage.background = ContextCompat.getDrawable(context, R.drawable.sim_mobile_2)
                else -> binding.simImage.background = ContextCompat.getDrawable(context, R.drawable.sim_else)
            }
        }
        
        private fun setContactBallColor(textView: TextView) {
            when((1..5).random()) {
                1 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_black)
                2 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_blue)
                3 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_green)
                4 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_red)
                5 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_puple)
            }
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