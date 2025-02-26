package com.gems.one_plan.ui.util

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.*
import java.util.*

@Composable
fun DatePickerDocked(
    context: Context,
    showDialog: Boolean,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(selectedDate)
                onDismiss()
            },
            year, month, day
        ).show()
    }
}
