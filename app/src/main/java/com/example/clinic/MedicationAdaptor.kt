package com.example.clinic

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_medication_item.view.*

class MedicationAdaptor( val context: Context) : RecyclerView.Adapter<MedicationAdaptor.ViewHolder>() {

    val items = ArrayList<Medication_item>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_medication_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val name = view.name
        val disease_name = view.disease_name
        val prescribed_on = view.prescribed_on
        val end_date = view.end_date
        val medicine = view.medicine

        fun bind(medication: Medication_item) {
            name.text = medication.doctorName
            disease_name.text = medication.diseaseName
            prescribed_on.text = medication.startDate.toString()
            end_date.text = medication.endDate.toString()
            var temp: String = ""
            for(medicineItem in medication.medicines) {
                temp += "\n"+medicineItem.name+"\t\t"+medicineItem.quantity
            }

        }
    }

    fun addItem(item : Medication_item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
}