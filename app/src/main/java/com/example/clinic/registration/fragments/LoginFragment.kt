package com.example.clinic.registration.fragments


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
import com.rahul.messmanagement.ui.registration.listeners.LoginInterfaceListener
import android.view.inputmethod.InputMethodManager
import com.example.clinic.R
import com.example.clinic.registration.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*





class LoginFragment : Fragment() {

    private val TAG = LoginFragment::class.java.simpleName
    private lateinit var loginInterfaceListener: LoginInterfaceListener
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginInterfaceListener = activity as LoginInterfaceListener
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rollNoTextView.text = LoginActivity.email
        passwordEditText.requestFocus()

        activateButton()
    }

    private fun activateButton() {
        continueButton.setOnClickListener {
            try {
                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
        val animator =
            ViewAnimationUtils.createCircularReveal(continueButton, cx, cy, finalRadius, 0f)
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

//        mAuth.signInWithEmailAndPassword(rollNoTextView.text.toString(), password).addOnCompleteListener {  }

        mAuth.signInWithEmailAndPassword(rollNoTextView.text.toString(), password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    passwordInputLayout.error = "Incorrect Password"
                    showButton()
                } else {
                    LoginActivity.password = password
                    loginInterfaceListener.switchToFragment(3)

                }
            }
    }

    private fun showButton() {

            val cx = continueButton.width / 2
            val cy = continueButton.height / 2

            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
            val animator =
                ViewAnimationUtils.createCircularReveal(continueButton, cx, cy, 0f, finalRadius)
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
