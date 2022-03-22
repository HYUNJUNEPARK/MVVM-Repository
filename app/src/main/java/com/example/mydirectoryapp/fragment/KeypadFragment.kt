package com.example.mydirectoryapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.databinding.FragmentKeyPadBinding

class KeypadFragment : Fragment(), View.OnClickListener {
    var _binding: FragmentKeyPadBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentKeyPadBinding.inflate(inflater, container, false)

        initButton()

        return binding.root
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.button0 -> buttonClicked("0")
            R.id.button1 -> buttonClicked("1")
            R.id.button2 -> buttonClicked("2")
            R.id.button3 -> buttonClicked("3")
            R.id.button4 -> buttonClicked("4")
            R.id.button5 -> buttonClicked("5")
            R.id.button6 -> buttonClicked("6")
            R.id.button7 -> buttonClicked("7")
            R.id.button8 -> buttonClicked("8")
            R.id.button9 -> buttonClicked("9")
            R.id.buttonStar -> buttonClicked("*")
            R.id.buttonHash -> buttonClicked("#")
            R.id.buttonDelete -> deleteButtonClicked()
            R.id.buttonCall -> callButtonClicked()
        }
    }

//start functions
    private fun initButton() {
        val buttonList = arrayOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9, binding.buttonStar, binding.buttonHash,
            binding.buttonCall, binding.buttonDelete
        )
        for (button in buttonList) {
            button.setOnClickListener(this)
        }
    }

    private fun buttonClicked(number: String) {
        binding.callNumberTextView.append(number)
    }

    private fun callButtonClicked() {
        val tel = "tel:${binding.callNumberTextView.text}"
        val intent = Intent(Intent.ACTION_CALL, Uri.parse(tel))
        startActivity(intent)
    }

    private fun deleteButtonClicked() {
        var numbers = binding.callNumberTextView.text
        if (numbers.isNotEmpty()){
            numbers =  numbers.substring(0, numbers.length -1)
            binding.callNumberTextView.text = numbers
        }
    }
}