package com.example.clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_medication.*
import kotlinx.android.synthetic.main.activity_medication.*
import java.lang.Exception
import java.lang.Integer.parseInt

class AddMedication : AppCompatActivity() {

    companion object  {
        var diseaseName : String = ""
        var lengthOfMedication : Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medication)
    }

    fun enterMedicines(view: View) {

        try {
            diseaseName = disease.text.toString()
            lengthOfMedication = parseInt(lengthofmedication.text.toString())

        }
        catch (e: Exception){
        }

        if(diseaseName == ""){
            Toast.makeText(applicationContext,"Disease Name cannot be empty",Toast.LENGTH_SHORT).show()
            return
        }
        if(lengthOfMedication == 0){
            Toast.makeText(applicationContext,"Length of medication cannot be empty",Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, Medication::class.java)
        startActivity(intent)
    }
}
