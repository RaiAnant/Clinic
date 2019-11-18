package com.example.clinic.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.rahul.clientapp.R
import com.rahul.clientapp.models.Appointment
import com.rahul.clientapp.models.Doctor
import org.w3c.dom.Text
import android.content.DialogInterface
import android.app.AlertDialog
import android.content.Context
import android.view.ContextThemeWrapper
import androidx.appcompat.content.res.AppCompatResources.getDrawable


class DoctorAdapter(var clientId: String, var clientName: String, var context: Context):
    RecyclerView.Adapter<DoctorAdapter.MyViewHolder>() {

    private val docList = ArrayList<Doctor>()
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun addItem(doc: Doctor){
        docList.add(doc)
        notifyItemInserted(docList.size-1)

    }


    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var docId: TextView = view.findViewById(R.id.textId)
        var docName: TextView = view.findViewById(R.id.textName)
        var docSpecialization: TextView = view.findViewById(R.id.textSpecialization)
        var docLocation: TextView = view.findViewById(com.rahul.clientapp.R.id.textLocation)

        fun bind(doc: Doctor, firebaseDatabase: FirebaseDatabase, appointment: Appointment, context: Context){
            docId.text = doc.docId
            docName.text = doc.name
            docSpecialization.text = doc.specialization
            docLocation.text = doc.location
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            firebaseDatabase.getReference("appointments/"+doc.docId).setValue(appointment)
                        }

                        DialogInterface.BUTTON_NEGATIVE -> {
                        }
                    }//Yes button clicked
                    //No button clicked
                }
            (view as CardView).setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show().getButton(DialogInterface.BUTTON_POSITIVE)
            }
        }


    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): DoctorAdapter.MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_doctor,parent,false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(docList[position], firebaseDatabase, Appointment(clientId, clientName), context)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = docList.size
}