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
import androidx.fragment.app.Fragment
import com.rahul.messmanagement.MessApplication

import com.rahul.messmanagement.R
import com.rahul.messmanagement.data.DataRepository
import com.rahul.messmanagement.data.network.NetworkResult
import com.rahul.messmanagement.ui.registration.MainActivity
import com.rahul.messmanagement.ui.registration.listeners.LoginInterfaceListener
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import android.view.inputmethod.InputMethodManager


class LoginFragment : Fragment(), CoroutineScope {

    private val TAG = LoginFragment::class.java.simpleName
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataRepository = (activity?.application as MessApplication).appComponent.getRepository()
        loginInterfaceListener = activity as LoginInterfaceListener
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataRepository.deleteAll()
        rollNoTextView.text = MainActivity.rollNo
        passwordEditText.requestFocus()

        activateButton()
    }
    private fun activateButton() {
        continueButton.setOnClickListener {
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
        val password = passwordEditText.text.toString()
        launch {
            val result = dataRepository.login(MainActivity.rollNo, password)
            when(result) {
                is NetworkResult.Ok -> {
                    Log.d(TAG, result.value.status.toString())

                    if(result.value.status) {
                        MainActivity.password = password
                        loginInterfaceListener.switchToFragment(3)
                    } else {
                        activity!!.runOnUiThread{
                            passwordInputLayout.error = "Incorrect Password"
                        }

                        showButton()
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

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    activateButton()
                }
            })

            animator.start()
        }

    }
}
