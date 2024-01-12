package com.zfml.noteapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        homeRoute()
        authenticationRoute()
    }
}

fun NavGraphBuilder.homeRoute (){
    composable(route = Screen.Home.route) {
        Column {
            Text (text = "Home")
        }
    }

}

fun NavGraphBuilder.authenticationRoute() {
    composable(route = Screen.Authentication.route) {

    }
}


