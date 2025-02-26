package com.gems.one_plan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.gems.one_plan.common.MyViewModel
import com.gems.one_plan.database.AppDatabase
import com.gems.one_plan.navigation.AppNavHost
import com.gems.one_plan.ui.theme.OnePlanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnePlanTheme {
                AppNavHost(
                    navController = rememberNavController(),
                    viewModel = MyViewModel(AppDatabase.getInstance(this))
                )
            }
        }
    }
}