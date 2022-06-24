package com.example.mydirectoryapp.vm

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydirectoryapp.model.Contact

class ContactVM: ViewModel() {
    //연락처 리스트의 길이 -> 옵저버가 관찰함
    //TODO contactListSize 와 contactListAll 의 사이즈를 동기화 시켜줘야함
    private var _contactListSize = MutableLiveData<Int>()
    val contactListSize: LiveData<Int>
        get() = _contactListSize

    //실제 연락처 리스트
    var _contactList = mutableListOf<Contact>()
    val contactList: MutableList<Contact>
       get() = _contactList


    //Test 용
    var _checker: MutableLiveData<Boolean>? = MutableLiveData<Boolean>()
    val checker: LiveData<Boolean>
        get() = _checker!!


    init {
        _contactListSize.value = contactList.size
        _checker = null
    }

    fun addContactIntent(context: Context) {
        _checker!!.value = _checker!!.value!!.not()
        Log.d("testLog", "checker: ${_checker!!.value}")


        var intent = Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI)
        context.startActivity(intent)

    }


}