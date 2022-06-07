package com.june.chattutorial.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.june.chattutorial.R
import com.june.chattutorial.databinding.ActivityLoginBinding
import com.june.chattutorial.key.FBVal.Companion.auth
import com.june.chattutorial.key.FBVal.Companion.currentUser
import com.june.chattutorial.key.FBVal.Companion.initCurrentUser
import com.june.chattutorial.key.UserIDPW.Companion.userA_ID
import com.june.chattutorial.key.UserIDPW.Companion.userA_PW
import com.june.chattutorial.key.UserIDPW.Companion.userB_ID
import com.june.chattutorial.key.UserIDPW.Companion.userB_PW
import com.june.chattutorial.network.NetworkConnectionCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val networkCheck: NetworkConnectionCallback by lazy { NetworkConnectionCallback(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        networkCheck.register()

        binding.loginActivity = this
    }

    override fun onResume() {
        super.onResume()
        binding.userAButton.isEnabled = true
        binding.userBButton.isEnabled = true
        currentUser = null
    }

    override fun onDestroy() {
        super.onDestroy()

        networkCheck.unregister()
    }

    fun userAButtonClicked() {
        binding.progressBar.visibility = View.VISIBLE
        binding.userAButton.isEnabled = false

        CoroutineScope(Dispatchers.IO).launch {
            auth.signInWithEmailAndPassword(userA_ID, userA_PW)
                .addOnCompleteListener { task ->
                    binding.progressBar.visibility = View.INVISIBLE
                    if (task.isSuccessful) {
                        initCurrentUser()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "로그인 에러", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun userBButtonClicked() {
        binding.userBButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.userBButton.isEnabled = false

            CoroutineScope(Dispatchers.IO).launch {
                auth.signInWithEmailAndPassword(userB_ID, userB_PW)
                    .addOnCompleteListener { task ->
                        binding.progressBar.visibility = View.INVISIBLE
                        if (task.isSuccessful) {
                            initCurrentUser()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(applicationContext, "로그인 에러", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}