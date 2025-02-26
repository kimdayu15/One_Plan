package com.gems.one_plan.ui.util

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.gems.one_plan.R
import com.gems.one_plan.database.Plans

@Composable
fun CustomChecker(
    plan: Plans,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val imageRes = when {
        isChecked -> R.drawable.checked
        !isChecked && plan.importance == 1 -> R.drawable.unchecked_normal
        !isChecked && plan.importance == 2 -> R.drawable.unchecked_high
        else -> R.drawable.unchecked_normal
    }

    IconButton(onClick = { onCheckedChange(!isChecked) }) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Custom checkbox"
        )
    }
}
