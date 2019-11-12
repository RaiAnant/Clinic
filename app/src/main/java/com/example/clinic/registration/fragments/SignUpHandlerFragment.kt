package com.example.clinic.registration.fragments


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.clinic.registration.LoginActivity
import com.google.firebase.auth.FirebaseAuth
//import com.rahul.messmanagement.MessApplication
//
//import com.rahul.messmanagement.R
//import com.rahul.messmanagement.data.DataRepository
//import com.rahul.messmanagement.data.network.NetworkResult
import com.rahul.messmanagement.ui.registration.listeners.LoginInterfaceListener
import kotlinx.android.synthetic.main.fragment_sign_up_handler.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpHandlerFragment : Fragment(), CoroutineScope {

    private val TAG = SignUpHandlerFragment::class.java.simpleName
    private lateinit var loginInterfaceListener: LoginInterfaceListener
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

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
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        return inflater.inflate(com.example.clinic.R.layout.fragment_sign_up_handler, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
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
                if(position == 2) {
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
//                2 -> SignUp3Fragment()
                else -> SigningUpFragment()
            }
        }

        override fun getCount(): Int {
            // Show 4 total pages.
            return 4
        }
    }

    private fun startSignUp() {
        var ref:DatabaseReference

        mAuth.createUserWithEmailAndPassword(LoginActivity.email, LoginActivity.password).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(TAG, "registration failed")
            }else{
                ref = database.getReference(LoginActivity.email)
                saveValuesToDatabase(ref)
                SigningUpFragment.showDone()
                Handler().postDelayed(Runnable {
                    loginInterfaceListener.switchToFragment(3)
                }, 200)
            }
        }

    }

    private fun saveValuesToDatabase(reference: DatabaseReference){
        reference.child("password").setValue(LoginActivity.password)
        reference.child("location").setValue(LoginActivity.location)
        reference.child("name").setValue(LoginActivity.name)
        reference.child("phNo").setValue(LoginActivity.phNo)
        reference.child("specialization").setValue(LoginActivity.specialization)


    }
}
