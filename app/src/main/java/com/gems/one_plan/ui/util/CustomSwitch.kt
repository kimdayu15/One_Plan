package com.gems.one_plan.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gems.one_plan.R


@Composable
fun CustomSwitch(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    IconButton(
        onClick = { onCheckedChange(!isChecked) }
    ) {
        Image(
            painter = painterResource(if (isChecked) R.drawable.switch_on else R.drawable.switch_off),
            contentDescription = "Custom Switch",
            modifier = Modifier.size(50.dp)
        )
    }
}