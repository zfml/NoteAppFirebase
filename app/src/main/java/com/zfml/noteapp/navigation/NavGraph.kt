package com.zfml.noteapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState
import com.zfml.noteapp.R
import com.zfml.noteapp.presentation.authentication.AuthenticationScreen
import com.zfml.noteapp.presentation.authentication.AuthenticationViewModel
import com.zfml.noteapp.presentation.home.HomeScreen
import com.zfml.noteapp.presentation.home.HomeViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeRoute(
            navigateToAuthScreen = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            }
        )
        authenticationRoute(
            navigateToHomeScreen = {
                navController.navigate(Screen.Home.route)
            }
        )
    }
}
fun NavGraphBuilder.authenticationRoute(
    navigateToHomeScreen: () -> Unit
) {
    composable(route = Screen.Authentication.route) {

        val viewModel: AuthenticationViewModel = viewModel()
        val loadingState by viewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val authenticated by viewModel.authenticated

        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            navigateToHome = {
                navigateToHomeScreen()
            }
        )

        OneTapSignInWithGoogle(
            state = oneTapState,
            clientId = stringResource(id = R.string.web_client_id),
            onTokenIdReceived = {tokenId ->
                viewModel.signInWithGoogle(
                    tokenId = tokenId,
                    onSuccess = {
                        viewModel.setLoading(false)
                    },
                    onError = {
                        viewModel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = {
                 viewModel.setLoading(false)
            }
        )

    }
}
fun NavGraphBuilder.homeRoute (
    navigateToAuthScreen: () -> Unit
){
    composable(route = Screen.Home.route) {
       val viewModel: HomeViewModel = viewModel()
        HomeScreen(
            name = viewModel.userName,
            onSignOut = {
               viewModel.signOut(
                   onSuccess = {
                       navigateToAuthScreen()
                   }
               )

            },
        )
    }

}




