package com.example.clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_medication__page.*

class Medication_Page : AppCompatActivity() {

    var patientName: String = ""
    var patientID : String = ""

    val medications: ArrayList<Medication_item> = ArrayList()
    private lateinit var mediationAdapter : MedicationAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication__page)
        val mi = Medication_item("aa", "bb", 1234, 123456, emptyList<Medicine>())
        rview.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        mediationAdapter =  MedicationAdaptor(this)
        rview.adapter = mediationAdapter

        mediationAdapter.addItem(mi)
        mediationAdapter.addItem(mi)
        mediationAdapter.addItem(mi)
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