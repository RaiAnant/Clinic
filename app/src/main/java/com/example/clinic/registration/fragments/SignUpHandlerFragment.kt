package com.rahul.messmanagement.ui.registration.fragments


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.rahul.messmanagement.MessApplication

import com.rahul.messmanagement.R
import com.rahul.messmanagement.data.DataRepository
import com.rahul.messmanagement.data.network.NetworkResult
import com.rahul.messmanagement.ui.registration.listeners.LoginInterfaceListener
import kotlinx.android.synthetic.main.fragment_sign_up_handler.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignUpHandlerFragment : Fragment(), CoroutineScope {

    private val TAG = SignUpHandlerFragment::class.java.simpleName
    private lateinit var dataRepository: DataRepository
    private lateinit var loginInterfaceListener: LoginInterfaceListener

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    companion object {
        lateinit var viewPager : ViewPager
    }

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_handler, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataRepository = (activity?.application as MessApplication).appComponent.getRepository()
        loginInterfaceListener = activity as LoginInterfaceListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = container
        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if(position == 3) {
                    startSignUp()
                }

            }

        })
        container.adapter = mSectionsPagerAdapter
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> SignUpFragment()
                1 -> SignUp2Fragment()
                2 -> SignUp3Fragment()
                else -> SigningUpFragment()
            }
        }

        override fun getCount(): Int {
            // Show 4 total pages.
            return 4
        }
    }

    private fun startSignUp() {
        launch {
            val result = dataRepository.signUp()
            when(result) {
                is NetworkResult.Ok -> {
                    Log.d(TAG, result.value.status.toString())

                    if(result.value.status) {
                        activity?.runOnUiThread{
                            SigningUpFragment.showDone()
                            Handler().postDelayed(Runnable {
                                loginInterfaceListener.switchToFragment(3)
                            }, 200)

                        }
                    }
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, result.exception.toString())
                }
                is NetworkResult.Exception -> {
                    Log.d(TAG, result.exception.toString())
                }
            }
        }
    }
}
