package com.example.drugstore.presentation.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.example.drugstore.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class TimePickerDialogFactory private constructor() {
    companion object {
        var lastHour = 0
        var lastMinute = 0

        fun setPreviousTime(time: Pair<Int, Int>) {
            lastHour = time.first
            lastMinute = time.second
        }

        fun create(
            context: Context,
            onTimeSelect: () -> Unit
        ): TimePickerDialog {
            return TimePickerDialog(
                context,
                { _, hour, minute ->
                    lastHour = hour
                    lastMinute = minute
                    onTimeSelect()
                },
                lastHour,
                lastMinute,
                true
            )
        }

        fun getTime() = Pair(lastHour, lastMinute)
    }
}