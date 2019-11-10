package com.rahul.messmanagement.ui.registration.fragments


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.rahul.messmanagement.MessApplication

import com.rahul.messmanagement.R
import com.rahul.messmanagement.data.DataRepository
import com.rahul.messmanagement.data.network.NetworkResult
import com.rahul.messmanagement.ui.registration.MainActivity
import com.rahul.messmanagement.ui.registration.listeners.LoginInterfaceListener
import kotlinx.android.synthetic.main.fragment_roll_no.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class RollNoFragment : Fragment(), CoroutineScope {

    private val TAG = RollNoFragment::class.java.simpleName
    private lateinit var dataRepository: DataRepository
    private lateinit var loginInterfaceListener: LoginInterfaceListener

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roll_no, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activateButton()
    }

    private fun activateButton() {
        continueButton.setOnClickListener {
            if(!rollNoEditText.text.toString().matches("[A-Z]{3}[0-9]{7}".toRegex())) {
                rollNoInputLayout.error = "Not a valid roll no"
                return@setOnClickListener
            }

            try {
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            } catch (e: Exception) {

            }

            deactivateButton()
            hideButton()
            tryLogin()
        }
    }

    private fun deactivateButton() {
        continueButton.setOnClickListener {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataRepository = (activity?.application as MessApplication).appComponent.getRepository()
        loginInterfaceListener = activity as LoginInterfaceListener
    }

    private fun hideButton() {
        val cx = continueButton.width / 2
        val cy = continueButton.height / 2

        val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val animator = ViewAnimationUtils.createCircularReveal(continueButton, cx, cy, finalRadius, 0f)
        animator.duration = 250L

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                continueButton.visibility = View.INVISIBLE
                continueProgressBar.visibility = View.VISIBLE
            }
        })

        animator.start()
    }

    private fun tryLogin() {
        val rollNo = rollNoEditText.text.toString()

        rollNoInputLayout.error = null

        launch {
            val result = dataRepository.checkRegistrationStatus(rollNo)
            when(result) {
                is NetworkResult.Ok -> {
                    Log.d(TAG, result.value.status.toString())

                    MainActivity.rollNo = rollNo
                    if(!result.value.status) {
                        loginInterfaceListener.switchToFragment(1)
                    } else {
                        loginInterfaceListener.switchToFragment(2)
                    }
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, result.exception.toString())
                    showButton()
                }
                is NetworkResult.Exception -> {
                    Log.d(TAG, result.exception.toString())
                    showButton()
                }
            }
        }
    }

    private fun showButton() {
        activity!!.runOnUiThread {
            val cx = continueButton.width / 2
            val cy = continueButton.height / 2

            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
            val animator = ViewAnimationUtils.createCircularReveal(continueButton, cx, cy, 0f, finalRadius)
            animator.duration = 250L

            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    continueButton.visibility = View.VISIBLE
                    continueProgressBar.visibility = View.GONE
                }
            })

            animator.start()
        }

    }
}
