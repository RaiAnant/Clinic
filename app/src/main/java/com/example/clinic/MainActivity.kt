package com.example.clinic

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clinic.Model.Appointment
import com.example.clinic.adapters.ClientAdapter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var count_of_clicks = 0;
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: ClientAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        var btn_edit =
            findViewById(R.id.floatingActionButton) as com.google.android.material.floatingactionbutton.FloatingActionButton
        btn_edit.setOnClickListener {

        }
        viewManager = LinearLayoutManager(this)
        viewAdapter = ClientAdapter(this)

        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        FirebaseDatabase.getInstance().getReference("/appointments/").addChildEventListener(object:
            ChildEventListener {
            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val app = p0.getValue(Appointment::class.java)!!
                viewAdapter.addItem(app)

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
        var pref:SharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        name_text.text = pref.getString(getString(R.string.doc_name),"Not able to fetch name")
        phonno_text.text = pref.getString(getString(R.string.phno),"Not able to fetch number")
        specialization_text.text = pref.getString(getString(R.string.specialization),"Not able to fetch data")
        id_text.text = pref.getString(getString(R.string.doc_id),"unable to fetch data")
    }


    private lateinit var mAuth: FirebaseAuth
}

