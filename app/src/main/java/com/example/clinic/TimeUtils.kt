package com.example.clinic

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun getTodaysMidninghtTimeInMillis() : Long {
        val currentInstance = Calendar.getInstance()

        currentInstance.set(Calendar.HOUR_OF_DAY, 0)
        currentInstance.set(Calendar.MINUTE, 0)
        currentInstance.set(Calendar.SECOND, 0)
        currentInstance.set(Calendar.MILLISECOND, 0)

        return currentInstance.timeInMillis
    }

    fun getDateString(timeStamp : Long) : String {
        val f = SimpleDateFormat("dd-MMM")
        return f.format(Date(timeStamp))
    }
}