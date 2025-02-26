package com.gems.one_plan.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gems.one_plan.common.MyViewModel
import com.gems.one_plan.database.Plans
import com.gems.one_plan.R


@Composable
fun ItemTask(plan: Plans, viewModel: MyViewModel, navController: NavController) {

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        CustomChecker(
            plan = plan,
            isChecked = plan.done,
            onCheckedChange = { newValue ->
                viewModel.updatePlan(plan.copy(done = newValue))
            }
        )

        Column (modifier = Modifier.fillMaxWidth().padding(top = 10.dp).weight(1f)){
            Row {
                if (plan.importance == 1 && plan.done == false) {
                    Image(painter = painterResource(R.drawable.low_imp), contentDescription = null)
                } else if (plan.importance == 2 && plan.done == false) {
                    Image(painter = painterResource(R.drawable.high_imp), contentDescription = null)
                }

                Text(
                    text = if (plan.text.length > 70) plan.text.take(70) + "..." else plan.text,
                    textDecoration = if (plan.done) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    },
                    maxLines = 3,
                    color = if (plan.done) {
                        Color.Gray
                    } else {
                        Color.Black
                    },
                    modifier = Modifier
                )
            }
            if (plan.deadline != null && plan.deadline != "Time has not been set yet") {
                Text(
                    text = plan.deadline,
                    color = Color.Gray
                )
            }
        }

        IconButton(onClick = {navController.navigate("item/${plan.id}")}) {
            Icon(
                painter = painterResource(R.drawable.info_outline_2),
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }


}


