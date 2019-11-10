package com.rahul.messmanagement.ui.registration.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.rahul.messmanagement.R
import kotlinx.android.synthetic.main.fragment_signing_up.*

class SigningUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signing_up, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = signUpDone
        fab.hide()
    }

    companion object {

        private lateinit var fab : FloatingActionButton
        fun showDone() {
            fab.show()
        }
    }
}
