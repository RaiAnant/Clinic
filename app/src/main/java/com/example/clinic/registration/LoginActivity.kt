package com.example.clinic.registration

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.clinic.R
import com.rahul.messmanagement.ui.registration.fragments.*
import com.rahul.messmanagement.ui.registration.listeners.LoginInterfaceListener

class LoginActivity : AppCompatActivity(), LoginInterfaceListener {

    companion object {
        val docId: String = ""
        val name: String = ""
        val specialization: String = ""
        val location: String = ""
    }

    private val TAG = LoginActivity::class.java.simpleName

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val isLoggedIn = sharedPref.getBoolean(getString(R.string.pref_loggedIn), false)

        if(fragment == null) {
            try {
                fragment = when(isLoggedIn) {
                    false -> RollNoFragment()
                    true -> VerifyFragment()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.loginContentView, fragment!!).commit()

    }

    override fun switchToFragment(fragmentNo: Int) {

        if(fragmentNo == 3) {
            saveDetails()
        } else if(fragmentNo == 4) {
            val sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putBoolean(getString(R.string.pref_Verified), true)
                commit()
            }
        }

        val nextFragment : Fragment = when(fragmentNo) {
            1 -> LoginFragment()
            2 -> SignUpHandlerFragment()
            3 -> VerifyFragment()
            else -> WelcomeFragment()
        }

//        val slide = Fade()
////        slide.slideEdge = Gravity.BOTTOM
//        nextFragment.enterTransition = slide

        supportFragmentManager.beginTransaction().
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).
            replace(R.id.loginContentView, nextFragment).commit()
    }

    private fun saveDetails() {
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(getString(R.string.pref_loggedIn), true)
            putString(getString(R.string.pref_rollNo),
                rollNo
            )
            putString(getString(R.string.pref_password),
                password
            )
            commit()
        }
    }
}
