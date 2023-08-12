package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(context: Context, navController:NavController, database: MenuDatabase) {
    val sharedPreferences = context.getSharedPreferences("MyPreffs", Context.MODE_PRIVATE)

    val startDestination: String
    if (sharedPreferences.getBoolean("userRegistered", false)) {
        startDestination = Home.route
    } else {
        startDestination = Onboarding.route
    }

    NavHost(navController = navController as NavHostController, startDestination){
        composable(Onboarding.route) {
            Onboarding(context = context, navHostController = navController)
        }
        composable(Home.route) {
            Home(context = context, navController, database)
        }

        composable(Profile.route) {
            Profile(context = context, navController)
        }
    }
}