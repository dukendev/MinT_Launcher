package com.dukendev.mintlauncher.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateUtil {
    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm:ss"

        private const val TIME_HHMM_FORMAT = "HH:mm"
        private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

        fun Date.formatDateWithDayOfMonthSuffix(): String {
            val dayOfMonth = SimpleDateFormat("dd", Locale.getDefault()).format(this)
            val month = SimpleDateFormat("MMM", Locale.getDefault()).format(this)
            val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(this)
            val dayOfWeek = SimpleDateFormat("E", Locale.getDefault()).format(this)

            val dayOfMonthInt = dayOfMonth.toInt()
            val dayOfMonthSuffix = when {
                dayOfMonthInt in 11..13 -> "th"
                dayOfMonthInt % 10 == 1 -> "st"
                dayOfMonthInt % 10 == 2 -> "nd"
                dayOfMonthInt % 10 == 3 -> "rd"
                else -> "th"
            }

            return "$dayOfMonth$dayOfMonthSuffix $month $year, $dayOfWeek"
        }


        fun Date.toDateString(): String {
            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            return dateFormat.format(this)
        }

        fun Date.toTimeString(): String {
            val timeFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
            return timeFormat.format(this)
        }


        fun Date.toTimeShortString(): String {
            val timeFormat = SimpleDateFormat(TIME_HHMM_FORMAT, Locale.getDefault())
            return timeFormat.format(this)
        }

        fun Date.toDateTimeString(): String {
            val dateTimeFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
            return dateTimeFormat.format(this)
        }


    }
}

