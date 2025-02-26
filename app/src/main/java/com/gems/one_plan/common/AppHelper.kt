package com.gems.one_plan.common

import java.text.SimpleDateFormat
import java.util.Date

class AppHelper {
    companion object {
        fun currentDate() : String{
            val date = Date()
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            return formatter.format(date)
        }
    }
}