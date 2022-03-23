package com.example.mydirectoryapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydirectoryapp.OnItemClick
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.activity.MainActivity.Companion.contactListAll
import com.example.mydirectoryapp.adapter.KeypadAdapter
import com.example.mydirectoryapp.databinding.FragmentKeyPadBinding
import com.example.mydirectoryapp.model.Contact

class KeypadFragment : Fragment(), View.OnClickListener, OnItemClick {
    lateinit var binding: FragmentKeyPadBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentKeyPadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButton()
        initRecyclerView(contactListAll)
        initCallNumberView()
        super.onViewCreated(view, savedInstanceState)
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

    //TODO binding 에서 초기화 예러가 뜨지 않는 이유 파악
    override fun onClick(number: String) {
        binding.callNumberTextView.text = number
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

    private fun initCallNumberView() {
        binding.callNumberTextView.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(inputNumber: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO ContactFragment SearchNumber 로직과 동일함 간소화 방법 생각해보기
                val searchList = mutableListOf<Contact>()
                for(contact in contactListAll) {
                    var contactNumber: String ? = null
                    contactNumber = contact.number.replace("-", "")
                    contactNumber = contactNumber.replace("(", "")
                    contactNumber = contactNumber.replace(")","")
                    contactNumber = contactNumber.replace(" ", "")
                    if (contactNumber.contains(inputNumber!!)) {
                        searchList.add(contact)
                    }
                }
                initRecyclerView(searchList)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun buttonClicked(number: String) {
        //TODO 전화 번호 포맷 함수
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
            //TODO 전화 번호 포맷 함수
            binding.callNumberTextView.text = numbers
        }
    }

    //TODO ContactFragment initRecyclerView 중복 함수
    private fun initRecyclerView(contactList: MutableList<Contact>) {
        val adapter = KeypadAdapter(requireContext(), this)
        adapter.searchList = contactList
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
    }
}