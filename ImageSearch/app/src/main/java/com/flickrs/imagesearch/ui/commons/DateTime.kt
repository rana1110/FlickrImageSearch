package com.flickrs.imagesearch.ui.commons

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateTime {

    @JvmStatic
    fun getFormattedDate(
        timestamp: String,
    ): String {
        val timestampFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val outputFormat = "MMM dd, yyyy HH:mm:ss"

        val dateFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone("UTC")

        val parser = SimpleDateFormat(timestampFormat, Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val date = parser.parse(timestamp)
            if (date != null) {
                dateFormatter.timeZone = TimeZone.getDefault()
                return dateFormatter.format(date)
            }
        } catch (e: Exception) {
            // Handle parsing error
            e.printStackTrace()
        }

        // If parsing fails, return the original timestamp
        return timestamp
    }
}