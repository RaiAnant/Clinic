package com.example.clinic

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.medication_list_item.view.*

class MedicineAdaptor( val context: Context) : RecyclerView.Adapter<MedicineAdaptor.ViewHolder>() {

    val items = ArrayList<Medicine>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.medication_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val medicineName = view.medicineName
        val quantity = view.quantityEntry
        val timeOfMedicine = view.timeOfMedication

        fun bind(medicine: Medicine) {
            medicineName.text = medicine.name
            Log.d("MwedicineAdaptor", medicine.name)
            var time = ""
            if(medicine.breakfast == 1) time += "Morning"
            if(medicine.lunch == 1) time += " Afternoon"
            if(medicine.dinner == 1) time += " Night"

            timeOfMedicine.text = time
            Log.d("MwedicineAdaptor", medicine.breakfast.toString())
            quantity.text = medicine.quantity
            time = ""
        }
    }

    fun addItem(item : Medicine) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
}

