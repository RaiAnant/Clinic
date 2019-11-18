package com.example.clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_medication.*
import java.lang.Exception
import java.lang.Integer.parseInt

class Medication : AppCompatActivity() {

    var name: String = ""
    var quantity: String = ""
    var breakfast: Int = 0
    var lunch: Int = 0
    var dinner: Int = 0
    var startDate : Long = 0
    var endDate : Long = 0
    var noDaysMedication : Int = 0


    val medicines = ArrayList<Medicine>()

    val databaseRef = FirebaseDatabase.getInstance().getReference("medications/dhruv/")

    private lateinit var medicationAdaptor : MedicineAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication)

        rv_medication.layoutManager = LinearLayoutManager(this)
        medicationAdaptor = MedicineAdaptor(this)
        rv_medication.adapter = medicationAdaptor
    }

    fun addMedicine(view: View) {

        name =  medicineName.text.toString()
        quantity = quantityEntry.text.toString()


        if(name==""){
            Toast.makeText(applicationContext,"Enter a Medicine",Toast.LENGTH_SHORT).show()
            return
        }
        if(quantity==""){
            Toast.makeText(applicationContext,"Enter quantity of dose",Toast.LENGTH_SHORT).show()
            return
        }

        startDate = TimeUtils.getTodaysMidninghtTimeInMillis()
        endDate = startDate + noDaysMedication*86400*1000

        if(checkBox.isChecked) breakfast=1
        if(checkBox2.isChecked) lunch=1
        if(checkBox3.isChecked) dinner=1

        val medicineItem = Medicine(name, quantity, breakfast, lunch, dinner)


        medicationAdaptor.addItem(medicineItem)
        medicines.add(medicineItem)

        quantityEntry.setText("")
        medicineName.setText("")

        checkBox!!.isChecked=false
        checkBox2!!.isChecked=false
        checkBox3!!.isChecked=false

    }

    fun submitData(view: View) {
        databaseRef.push().setValue(Medication_item("Ambuj", AddMedication.diseaseName, System.currentTimeMillis(),
            (TimeUtils.getTodaysMidninghtTimeInMillis()+AddMedication.lengthOfMedication*86400*1000).toLong(), medicines))

        Toast.makeText(applicationContext,"Medication Uploaded",Toast.LENGTH_SHORT).show()

        val intent = Intent(this, AddMedication::class.java)
        startActivity(intent)

    }
}
