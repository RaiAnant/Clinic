package com.rahul.messmanagement.ui.registration.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

import com.rahul.messmanagement.R
import com.rahul.messmanagement.customuielements.ReselectableSpinner
import com.rahul.messmanagement.ui.registration.MainActivity
import kotlinx.android.synthetic.main.fragment_sign_up2.*


class SignUp2Fragment : Fragment() , ReselectableSpinner.OnSpinnerCancelledListener {

    private var selectedPosition = -1
    private var allMessList = arrayOf("BH1", "BH2", "BH3", "BH4", "BH5", "GH1", "GH2", "GH3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText.setText(MainActivity.rollNo.toLowerCase() + "@iiita.ac.in")
        messEditText.showSoftInputOnFocus = false
        val messAdapter = ArrayAdapter<String>(context!!, R.layout.list_item_spinner_drop_down, allMessList)
        messSpinnerView.adapter = messAdapter


        messEditText.onFocusChangeListener = View.OnFocusChangeListener{_, hasFocus ->
            if(hasFocus) {
                messSpinnerView.performClick()
            }
        }

        messSpinnerView.onAnyItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                messEditText.clearFocus()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                messEditText.setText(allMessList[position])
                selectedPosition = position
                messEditText.clearFocus()
            }

        }

        button.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val mess = messEditText.text.toString()

            if(name.isEmpty()){
                nameInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                nameInputLayout.error = null
            }

            if(email.isEmpty()) {
                emailInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                emailInputLayout.error = null
            }

            if(mess.isEmpty()) {
                messInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                messInputLayout.error = null
            }

            MainActivity.name = name
            MainActivity.email = email
            MainActivity.mess = mess

            SignUpHandlerFragment.viewPager.currentItem = 2
        }
    }

    override fun onSpinnerCancelled() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
