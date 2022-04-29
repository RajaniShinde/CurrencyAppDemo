package com.example.currencyapp.framework.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class DateUtils {

    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        fun getEndDate(): String {
            return LocalDate.now().toString()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getStartDate(): String {
            return LocalDate.now().minusDays(3).toString()
        }
    }
}
