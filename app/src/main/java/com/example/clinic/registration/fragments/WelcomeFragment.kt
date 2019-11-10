package com.rahul.messmanagement.ui.registration.fragments


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rahul.messmanagement.MessApplication

import com.rahul.messmanagement.R
import com.rahul.messmanagement.data.DataRepository
import com.rahul.messmanagement.data.network.NetworkResult
import com.rahul.messmanagement.ui.registration.MainActivity
import com.rahul.messmanagement.ui.registration.listeners.LoginInterfaceListener
import com.rahul.messmanagement.data.network.networkmodels.User
import com.rahul.messmanagement.ui.HomeActivity
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WelcomeFragment : Fragment(), CoroutineScope {

    private val TAG = WelcomeFragment::class.java.simpleName
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
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataRepository = (activity?.application as MessApplication).appComponent.getRepository()
        loginInterfaceListener = activity as LoginInterfaceListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDetails()
    }

    fun getDetails() {
        launch {
            val result = dataRepository.loginGet(MainActivity.rollNo)

            when(result) {
                is NetworkResult.Ok -> {
                    Log.d(TAG, result.value.name)

                    val result2 = dataRepository.getAttendanceFromServer(MainActivity.rollNo)
                    when(result2) {
                        is NetworkResult.Ok -> {
                            dataRepository.saveAllAttendance(result2.value)

                            val result3 = dataRepository.getMenuFromServer(result.value.mess)

                            when(result3) {
                                is NetworkResult.Ok -> {
                                    dataRepository.saveAllMenu(result3.value)
                                    activity?.runOnUiThread {
                                        saveDetails(result.value)

                                        showButton()
                                    }
                                }
                            }

                        }
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

    private fun saveDetails(user: User) {
        val sharedPref = activity!!.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(getString(R.string.pref_loggedIn), true)
            putString(getString(R.string.pref_rollNo), user.rollNo)
            putString(getString(R.string.pref_password), user.password)
            putString(getString(R.string.pref_email), user.email)
            putString(getString(R.string.pref_name), user.name)
            putString(getString(R.string.pref_mess), user.mess)
            putString(getString(R.string.pref_holderName), user.accountHolderName)
            putString(getString(R.string.pref_accountNo), user.accountNo)
            putString(getString(R.string.pref_ifscCode), user.IFSCCode)
            putString(getString(R.string.pref_bankBranch), user.bankBranch)
            putString(getString(R.string.pref_bankName), user.bankName)
            commit()
        }

        startActivity(Intent(activity, HomeActivity::class.java))
        activity?.finish()
    }

    private fun showButton() {
        activity!!.runOnUiThread {
            val cx = button2.width / 2
            val cy = button2.height / 2

            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
            val animator = ViewAnimationUtils.createCircularReveal(button2, cx, cy, 0f, finalRadius)
            animator.duration = 250L

            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    button2.visibility = View.VISIBLE
                    progressBar2.visibility = View.INVISIBLE
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)

                }
            })

            animator.start()
        }

    }

}
