package com.gems.one_plan.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gems.one_plan.common.MyViewModel
import com.gems.one_plan.database.Plans
import com.gems.one_plan.ui.util.CustomSwitch
import com.gems.one_plan.ui.util.DatePickerDocked
import com.gems.one_plan.ui.util.DeleteDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(id: String, navController: NavHostController, viewModel: MyViewModel) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var text by remember { mutableStateOf("") }
    var selectedImportance by remember { mutableStateOf("None") }
    var selectedDate by remember { mutableStateOf("Time has not been set yet") }
    var expanded by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    val plan by viewModel.getPlanById(id.toInt()).collectAsState(initial = null)

    if (id != "-1") {
        LaunchedEffect(plan) {
            plan?.let {
                text = it.text
                selectedDate = it.deadline ?: "Time has not been set yet"
                selectedImportance = when (it.importance) {
                    0 -> "None"
                    1 -> "Low"
                    2 -> "High"
                    else -> "None"
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F6F2)),
        topBar = {
            Box(
                modifier = Modifier.shadow(
                    elevation = if (scrollState.value > 0.5f) 10.dp else 0.dp
                )
            ) {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                "save".uppercase(),
                                fontSize = 17.sp,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .clickable {
                                        if (text.isNotEmpty()) {
                                            val newPlan = Plans(
                                                text = text,
                                                deadline = selectedDate,
                                                importance = when (selectedImportance) {
                                                    "None" -> 0
                                                    "Low" -> 1
                                                    "High" -> 2
                                                    else -> 0
                                                }
                                            )
                                            if (id != "-1") {
                                                viewModel.updatePlan(newPlan)
                                            } else {
                                                viewModel.addPlan(newPlan)
                                            }
                                            navController.popBackStack()
                                        }
                                    },
                                color = Color(0xFF007AFF)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp, 7.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                minLines = 3,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }),
                placeholder = { Text("Need to do...") }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable { expanded = true }
            ) {
                Column {
                    Text("Importance", fontSize = 17.sp)
                    Text(selectedImportance, color = Color.Gray, fontSize = 14.sp)
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.size(150.dp).background(Color.White)
                    ) {
                        listOf("None", "Low", "High").forEach { importance ->
                            DropdownMenuItem(
                                text = { Text(importance) },
                                onClick = {
                                    selectedImportance = importance
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Deadline", fontSize = 17.sp)
                    Text(selectedDate, color = Color.Gray, fontSize = 14.sp)
                }
                CustomSwitch(
                    isChecked = checked,
                    onCheckedChange = {
                        checked = it
                        if (checked) {
                            showDatePicker = true
                        } else {
                            selectedDate = "Time has not been set yet"
                        }
                    }
                )
            }

            DatePickerDocked(
                context = context,
                showDialog = showDatePicker,
                onDateSelected = { date -> selectedDate = date },
                onDismiss = {
                    showDatePicker = false
                    checked = false
                }
            )

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            DeleteDialog(
                openDialog = openDialog,
                onDismissRequest = { openDialog = false },
                onConfirmation = {
                    if (id != "-1") {
                        viewModel.deletePlan(Plans(id.toInt()))
                    }
                    navController.popBackStack()
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable { openDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.size(7.dp))
                Text("Delete", color = Color.Red, fontSize = 17.sp)
            }
        }
    }
}

