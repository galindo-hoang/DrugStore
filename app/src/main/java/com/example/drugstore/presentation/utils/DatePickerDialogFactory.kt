package com.example.drugstore.presentation.utils

import android.app.DatePickerDialog
import android.content.Context
import com.example.drugstore.utils.Constants
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class DatePickerDialogFactory private constructor() {
    companion object {
        private var calendar = Calendar.getInstance();
        val selectedYear = calendar.get(Calendar.YEAR)
        val selectedMonth = calendar.get(Calendar.MONTH)
        val selectedDay = calendar.get(Calendar.DAY_OF_MONTH)

        fun setPreviousDate(date: Date) {
            calendar.time = date
        }

        fun create(
            context: Context,
            onDateSelected: () -> Unit
        ): DatePickerDialog {
            return DatePickerDialog(
                context,
                { _, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    onDateSelected()
                },
                selectedYear,
                selectedMonth,
                selectedDay
            )
        }

        fun getDate(): Date = calendar.time
    }
}