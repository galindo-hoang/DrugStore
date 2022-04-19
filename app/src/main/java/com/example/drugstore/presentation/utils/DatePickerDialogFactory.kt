package com.example.drugstore.presentation.utils

import android.app.DatePickerDialog
import android.content.Context
import com.example.drugstore.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class DatePickerDialogFactory private constructor() {
    companion object {
        val cal: Calendar = Calendar.getInstance()

        fun create(
            context: Context,
            onDateSelected: () -> Unit
        ): DatePickerDialog {
            return DatePickerDialog(
                context,
                { _, year, month, day ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, day)
                    onDateSelected()
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
        }
    }
}