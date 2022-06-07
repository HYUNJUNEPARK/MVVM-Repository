package com.june.chattutorial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel: ViewModel() {
    val _chatListLiveDataSize = MutableLiveData<Int>()
    val chatListLiveDataSize: LiveData<Int>
        get() = _chatListLiveDataSize
}