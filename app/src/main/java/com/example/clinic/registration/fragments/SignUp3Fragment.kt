package com.rahul.messmanagement.ui.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.rahul.messmanagement.R
import com.rahul.messmanagement.ui.registration.MainActivity
import kotlinx.android.synthetic.main.fragment_sign_up3.*

class SignUp3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up3,  container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            val name = holderNameEditText.text.toString()
            val accNo = accountNumberEditText.text.toString()
            val ifscCode = ifscEditText.text.toString()
            val bankName = bankNameEditText.text.toString()
            val bankBranch = bankBranchEditText.text.toString()

            if(name.isEmpty()){
                holderNameInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                holderNameInputLayout.error = null
            }

            if(accNo.length < 8) {
                accountNumberInputLayout.error = "Must be at least 8 digits"
                return@setOnClickListener
            } else {
                accountNumberInputLayout.error = null
            }

            if(ifscCode.isEmpty()) {
                ifscInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                ifscInputLayout.error = null
            }

            if(bankName.isEmpty()) {
                bankNameInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                bankNameInputLayout.error = null
            }

            if(bankBranch.isEmpty()) {
                bankBranchInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                bankBranchInputLayout.error = null
            }

            MainActivity.accountHolderName = name
            MainActivity.accountNo = accNo
            MainActivity.ifscCode = ifscCode
            MainActivity.bankName = bankName
            MainActivity.bankBranch = bankBranch

            SignUpHandlerFragment.viewPager.currentItem = 3
        }
    }
}