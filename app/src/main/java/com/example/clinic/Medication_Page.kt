package com.example.clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_medication__page.*

class Medication_Page : AppCompatActivity() {

    companion object {
        var patientNameText: String = ""
        var patientIDText: String = ""

    }
    val medications: ArrayList<Medication_item> = ArrayList()
    private lateinit var mediationAdapter : MedicationAdaptor
    private var childEventListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {

        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {

        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            Log.d("Firebase", p0.value.toString())
            val data = p0.getValue(Medication_item::class.java)!!
            Log.d("Firebase", data.doctorName)
            mediationAdapter.addItem(data)
        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication__page)

        patientName.text = patientNameText

        patientNameText = intent.getStringExtra("name")
        patientIDText = intent.getStringExtra("uid")

        rview.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        mediationAdapter =  MedicationAdaptor(this)
        rview.adapter = mediationAdapter

        FirebaseDatabase.getInstance().getReference("/medications/" + patientIDText).addChildEventListener(childEventListener)
    }

    fun addReport(view: View) {
        val intent = Intent(applicationContext, ReportEntry::class.java)
        startActivity(intent)
    }
    fun addMedication(view: View) {
        val intent = Intent(applicationContext, AddMedication::class.java)
        startActivity(intent)
    }
}