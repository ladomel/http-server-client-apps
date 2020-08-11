package com.example.http_clinet_app.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        fun getTimeElapsed(date: Date): String {
            val diff = Date().time - date.time
            if (diff < 60000) {
                return "Now"
            }
            if (diff < 60 * 60000) {
                return SimpleDateFormat("m").format(Date(diff)) + " min"
            }
            if (diff < 24 * 60 * 60000) {
                return SimpleDateFormat("H").format(Date(diff)) + " hours"
            }
            return SimpleDateFormat("dd/mm/yyyy").format(date)
        }
    }
}