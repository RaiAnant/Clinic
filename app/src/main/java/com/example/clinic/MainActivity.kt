package com.example.clinic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnCompleteListener<AuthResult> {

    override fun onComplete(p0: Task<AuthResult>) {
        var toast: Toast = Toast.makeText(applicationContext, "Registered", Toast.LENGTH_LONG)
        toast.show()
    }

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

    }

    private fun registerUser(email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this)

    }
}
