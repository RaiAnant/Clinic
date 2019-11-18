package com.example.clinic.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.clinic.AddMedication
import com.example.clinic.Medication_Page
import com.example.clinic.Model.Appointment
import com.example.clinic.Model.Doctor
import com.example.clinic.R
import com.google.firebase.database.FirebaseDatabase
import java.lang.Appendable

class ClientAdapter(var context: Context):
    RecyclerView.Adapter<ClientAdapter.MyViewHolder>() {

    private val appoinList = ArrayList<Appointment>()
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun addItem(appointment: Appointment){
        appoinList.add(appointment)
        notifyItemInserted(appoinList.size-1)

    }


    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var name: TextView = view.findViewById(R.id.textName)
        var date: TextView = view.findViewById(R.id.textDate)


        fun bind(appointment: Appointment, context: Context){
            name.text = appointment.clientName
            date.text = appointment.date
//            val dialogClickListener =
//                DialogInterface.OnClickListener { dialog, which ->
//                    when (which) {
//                        DialogInterface.BUTTON_POSITIVE -> {
//                            firebaseDatabase.getReference("appointments/"+doc.docId).setValue(appointment)
//                        }
//
//                        DialogInterface.BUTTON_NEGATIVE -> {
//                        }
//                    }//Yes button clicked
//                    //No button clicked
//                }
            (view as CardView).setOnClickListener {
                var intent: Intent = Intent(context, Medication_Page::class.java)
                intent.putExtra("uid", appointment.clientId)
                intent.putExtra("name", appointment.clientName)
                context.startActivity(intent)
            }
        }


    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ClientAdapter.MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_appointments,parent,false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(appoinList[position], context)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = appoinList.size
}