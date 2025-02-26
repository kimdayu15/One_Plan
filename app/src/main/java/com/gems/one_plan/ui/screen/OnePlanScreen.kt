package com.gems.one_plan.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.gems.one_plan.R
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gems.one_plan.common.MyViewModel
import com.gems.one_plan.ui.util.ItemTask
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnePlanScreen(viewModel: MyViewModel, navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val visibility = remember { mutableStateOf(true) }

    val allPlans = viewModel.plans.collectAsState(emptyList())

    val displayedList = if (visibility.value) {
        allPlans.value
    } else {
        allPlans.value.filter { !it.done }
    }

    val doneCount = allPlans.value.count { it.done }


    Scaffold(topBar = {
        LargeTopAppBar(
            modifier = Modifier.background(Color(0xFFF7F6F2)), title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "My Todos",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (scrollBehavior.state.collapsedFraction < 0.5f) {
                            Text(
                                text = "Done - $doneCount",
                                fontSize = 20.sp,
                                color = Color(0x4D000000)
                            )
                        }
                    }
                    IconButton(onClick = {
                        visibility.value = !visibility.value
                    }) {
                        Icon(
                            painter = painterResource(
                                if (visibility.value) {
                                    R.drawable.baseline_visibility_24
                                } else {
                                    R.drawable.baseline_visibility_off_24
                                }
                            ), contentDescription = "Done", tint = Color(0xFF007AFF)
                        )
                    }
                }
            }, scrollBehavior = scrollBehavior
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate("item/-1") },
            shape = CircleShape,
            contentColor = Color.White,
            containerColor = Color(0xFF007AFF)
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp, 6.dp)
                ) {
                    LazyColumn {
                        items(
                            items = displayedList.reversed(),
                            key = { plan -> plan.id }
                        ) { plan ->

                            val delete = SwipeAction(
                                onSwipe = { viewModel.deletePlan(plan) },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                },
                                background = Color.Red
                            )

                            val makeDone = SwipeAction(
                                onSwipe = {
                                    viewModel.updatePlan(plan.copy(done = true))
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                },
                                background = Color(0xFF34C759)

                            )

                            SwipeableActionsBox(
                                startActions = listOf(makeDone),
                                endActions = listOf(delete)
                            )
                            {
                                ItemTask(
                                    plan = plan,
                                    viewModel = viewModel,
                                    navController = navController
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

