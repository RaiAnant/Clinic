package com.example.clinic

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
}