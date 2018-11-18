package com.progressor.progressor.service

import java.text.SimpleDateFormat
import java.util.*

fun dateTimeStampToDisplay(originalDate: String): String {
    val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
    val toFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
    val date = fromFormat.parse(originalDate)
    return toFormat.format(date)
}