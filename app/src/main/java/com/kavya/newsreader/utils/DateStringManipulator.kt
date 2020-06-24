package com.kavya.newsreader.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Kavya P S on 18/06/20.
 */
object DateStringManipulator {
    fun dateTimezoneRemover(input: String): String {
        val formattedDate = input.trim().split("T")[0]
        return if (formattedDate.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})".toRegex())
            && isThisDateValid(formattedDate, "yyyy-MM-dd")
        ) {
            println("date is $formattedDate")
            formattedDate
        } else {
            println("date is ''")
            ""
        }
    }

    private fun isThisDateValid(dateToValidate: String?, dateFormat: String): Boolean {
        dateToValidate?.let {
            try {
                val simpleDateFormat = SimpleDateFormat(dateFormat)
                val date: Date? = simpleDateFormat.parse(dateToValidate)
                println(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return false
            }
            return true
        }
        return false

    }
}
