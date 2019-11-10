package com.rahul.messmanagement.ui.registration.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rahul.messmanagement.MessApplication

import com.rahul.messmanagement.R
import com.rahul.messmanagement.data.DataRepository
import com.rahul.messmanagement.data.network.NetworkResult
import com.rahul.messmanagement.ui.registration.MainActivity
import com.rahul.messmanagement.ui.registration.listeners.LoginInterfaceListener
import kotlinx.android.synthetic.main.fragment_verify.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class VerifyFragment : Fragment(), CoroutineScope {

    private val TAG = VerifyFragment::class.java.simpleName
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
        return inflater.inflate(R.layout.fragment_verify, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataRepository = (activity?.application as MessApplication).appComponent.getRepository()
        loginInterfaceListener = activity as LoginInterfaceListener
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        MainActivity.rollNo = sharedPref!!.getString(getString(R.string.pref_rollNo), "")
        MainActivity.password = sharedPref.getString(getString(R.string.pref_password), "")

        checkStatus()
    }

    private fun checkStatus() {
        launch {
            val result = dataRepository.login(MainActivity.rollNo, MainActivity.password)
            when(result) {
                is NetworkResult.Ok -> {
                    Log.d(TAG, "Verified" + result.value.verified.toString())

                    if(result.value.verified) {
                        loginInterfaceListener.switchToFragment(4)
                    } else {
                        activity!!.runOnUiThread{
                            signUpProgressBar.visibility = View.INVISIBLE
                            textView3.text = "Account Not Verified"
                            textView6.visibility = View.VISIBLE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, result.exception.toString())
                    Toast.makeText(context, "Some error in the server. Try some time later.", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Exception -> {
                    Log.d(TAG, result.exception.toString())
                    Toast.makeText(context, "Error. Try checking if internet is on", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
