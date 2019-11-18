package com.example.clinic

data class Medication_item(val doctorName: String,
                      val diseaseName: String,
                      var startDate: Long,
                      var endDate: Long,
                      val medicines: List<Medicine>)