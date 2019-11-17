package com.example.clinic.registration

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.clinic.R
import com.example.clinic.registration.fragments.*
import com.rahul.messmanagement.ui.registration.listeners.LoginInterfaceListener

class LoginActivity : AppCompatActivity(), LoginInterfaceListener {

    companion object {

        var docId: String = ""
        var name: String = ""
        var password: String = ""
        var specialization: String = ""
        var location: String = ""
        var phNo: String = ""
        var email: String = ""
    }

    private val TAG = LoginActivity::class.java.simpleName

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(fragment == null) {
            try {
                fragment = RollNoFragment()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.loginContentView, fragment!!).commit()

    }

    override fun switchToFragment(fragmentNo: Int) {



        val nextFragment : Fragment = when(fragmentNo) {
            1 -> LoginFragment()
            2 -> SignUpHandlerFragment()
            else -> WelcomeFragment()
        }

//        val slide = Fade()
////        slide.slideEdge = Gravity.BOTTOM
//        nextFragment.enterTransition = slide

        supportFragmentManager.beginTransaction().
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).
            replace(R.id.loginContentView, nextFragment).commit()
    }


}
