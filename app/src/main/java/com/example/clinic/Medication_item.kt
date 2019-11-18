package com.example.clinic

data class Medication_item(val doctorName: String = "",
                      val diseaseName: String = " ",
                      var startDate: Long = 0L,
                      var endDate: Long = 0L,
                      val medicines: List<Medicine> = emptyList())