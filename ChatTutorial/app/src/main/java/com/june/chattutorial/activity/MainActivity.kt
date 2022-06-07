package com.june.chattutorial.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.june.chattutorial.R
import com.june.chattutorial.adapter.ChatItemAdapter
import com.june.chattutorial.databinding.ActivityMainBinding
import com.june.chattutorial.key.DBKey.Companion.DB_CHAT_TUTORIAL
import com.june.chattutorial.key.DBKey.Companion.MESSAGE
import com.june.chattutorial.key.DBKey.Companion.SENDER_ID
import com.june.chattutorial.key.DBKey.Companion.SEND_TIME
import com.june.chattutorial.key.FBVal.Companion.currentUser
import com.june.chattutorial.key.FBVal.Companion.firebaseDBReference
import com.june.chattutorial.key.UserIDPW.Companion.TAG
import com.june.chattutorial.key.UserIDPW.Companion.userA_ID
import com.june.chattutorial.key.UserIDPW.Companion.userA_UID
import com.june.chattutorial.key.UserIDPW.Companion.userB_UID
import com.june.chattutorial.model.ChatItemModel
import com.june.chattutorial.network.NetworkConnectionCallback
import com.june.chattutorial.viewmodel.ChatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentUserDB: DatabaseReference
    private lateinit var partnerUserDB: DatabaseReference
    private val chatList = mutableListOf<ChatItemModel>()
    private val adapter = ChatItemAdapter(this)
    private lateinit var partnerUid: String
    private val chatViewModel: ChatViewModel by viewModels()
    private val networkCheck: NetworkConnectionCallback by lazy { NetworkConnectionCallback(this) }

    private val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            chatList.clear()
            for (chatDialogue in snapshot.children) {
                val chatDialogueMap = chatDialogue.value as HashMap<String, String>
                val chatModel = ChatItemModel(
                    senderId = chatDialogueMap[SENDER_ID],
                    message = chatDialogueMap[MESSAGE],
                    sendTime = chatDialogueMap[SEND_TIME].toString().toLong()
                )
                chatList.add(chatModel)
                adapter.submitList(chatList)
                //ViewModel LiveData update
                chatViewModel._chatListLiveDataSize.value = chatList.size
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.d(TAG, "MainActivity valueEventListener Error : $error")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.mainActivity = this
        binding.lifecycleOwner = this
        binding.viewModel = chatViewModel

        networkCheck.register()

        initFirebaseDatabase()
        initCurrentUserUI()
        initRecyclerView()
        liveDataObserver()
    }

    override fun onDestroy() {
        super.onDestroy()

        networkCheck.unregister()
        currentUserDB.removeEventListener(valueEventListener)
    }

    private fun liveDataObserver() {
        val liveDataObserver: Observer<Int> = Observer { _->
            adapter.notifyDataSetChanged()
            //리사이클러 뷰 위치 조절
            binding.recyclerView.run {
                postDelayed( {scrollToPosition(adapter!!.itemCount - 1)}, 300)
            }
        }
        chatViewModel.chatListLiveDataSize.observe(
            this,
            liveDataObserver
        )
    }

    private fun initFirebaseDatabase() {
        partnerUid = if (currentUser!!.uid == userA_UID) userB_UID else userA_UID

        currentUserDB =
            firebaseDBReference
                .child(DB_CHAT_TUTORIAL)
                .child(currentUser!!.uid)

        partnerUserDB =
            firebaseDBReference
                .child(DB_CHAT_TUTORIAL)
                .child(partnerUid)
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        currentUserDB.addValueEventListener(valueEventListener)
    }

    private fun initCurrentUserUI() {
        binding.userIdTextView.text = currentUser!!.email.toString()
        if (currentUser!!.email == userA_ID) {
            binding.userProfileImageView.background = ResourcesCompat.getDrawable(resources, R.drawable.user_a, null)
        } else {
            binding.userProfileImageView.background = ResourcesCompat.getDrawable(resources, R.drawable.user_b, null)
        }
    }

    fun messageSendButtonClicked() {
        val chatItem = ChatItemModel(
            senderId = currentUser!!.uid,
            message = binding.messageEditText.text.toString(),
            sendTime = System.currentTimeMillis()
        )
        CoroutineScope(Dispatchers.IO).launch {
            currentUserDB
                .push()
                .setValue(chatItem)
            partnerUserDB
                .push()
                .setValue(chatItem)
        }
        binding.messageEditText.text = null
    }
}