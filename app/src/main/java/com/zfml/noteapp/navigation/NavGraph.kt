package com.zfml.noteapp.navigation

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState
import com.zfml.noteapp.R
import com.zfml.noteapp.presentation.screen.authentication.AuthenticationScreen
import com.zfml.noteapp.presentation.screen.authentication.AuthenticationViewModel
import com.zfml.noteapp.presentation.components.DisplayAlertDialog
import com.zfml.noteapp.presentation.screen.home.HomeScreen
import com.zfml.noteapp.presentation.screen.home.HomeViewModel
import com.zfml.noteapp.presentation.screen.write.WriteScreen
import com.zfml.noteapp.presentation.screen.write.WriteViewModel
import com.zfml.noteapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
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
            },
            navigateToWriteScreen = {
                navController.navigate(Screen.Write.route)
            },
            navigateToWriteWithArg = {
                navController.navigate(Screen.Write.passNoteId(it))
            }
        )
        authenticationRoute(
            navigateToHomeScreen = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        writeRoute(
            onNavigateToHome = {
                navController.navigateUp()
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
            rememberAccount = false,
            onTokenIdReceived = { tokenId ->
                    viewModel.signInWithGoogle(
                        tokenId = tokenId,
                        onSuccess = {
                            viewModel.setLoading(false)
                            navigateToHomeScreen()
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
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeRoute (
    navigateToAuthScreen: () -> Unit,
    navigateToWriteScreen: () -> Unit,
    navigateToWriteWithArg: (String) -> Unit
){
    composable(route = Screen.Home.route) {
       val viewModel: HomeViewModel = hiltViewModel()
       val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
       val scope = rememberCoroutineScope()

        var openSignOutDialog by remember{mutableStateOf(false) }
        val context = LocalContext.current






        HomeScreen(
            userName = viewModel.userName,
            userImage = viewModel.image.toString(),
            notesResponse = viewModel.notesRespones,
            drawerState = drawerState,
            openDrawerClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onNavigateToWrite = navigateToWriteScreen,
            onNavigateToWriteWithArg = navigateToWriteWithArg
            ,
            onSignOutClicked = { openSignOutDialog = true },
        )

        DisplayAlertDialog(
            title = "Sign Out",
            description = "Are you sure you want to sign out your account?",
            openDialog = openSignOutDialog,
            onClosedDialog = {
                  openSignOutDialog = false
            },
            onConfirmClicked = {
                viewModel.signOut(
                   onSuccess = {
                       openSignOutDialog = false
                       navigateToAuthScreen()
                   }
               )
            }) {

        }



    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.writeRoute(
    onNavigateToHome: () -> Unit
) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(
            navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        val context  = LocalContext.current
        val viewModel : WriteViewModel = hiltViewModel()
        WriteScreen(
        onNavigateToHome = onNavigateToHome,
        createdDate = viewModel.createdDate.value,
        noteTitle = viewModel.noteTitle,
        noteDescription = viewModel.noteDescription,
        onTitleChanged = {viewModel.onChangedTitle(it)},
        onTitleFocusChanged = { viewModel.onChangedTitleFocus(it)},
        onDescriptionChanged = {viewModel.onChangedDescription(it)},
        onDescriptionFocusChanged = {viewModel.onChangedDescriptionFocus(it)},
        onDeleteNote = {
            viewModel.deleteNote()
            onNavigateToHome()
        },
        onSaveNote = {

               if(viewModel.IsValidInput()) {
                   viewModel.saveNote(
                       onSuccess = {
                           Toast.makeText(
                               context,
                               "Successfully Added",
                               Toast.LENGTH_SHORT
                           ).show()
                           onNavigateToHome()
                       },
                       onError = {
                           Toast.makeText(
                               context,
                               it,
                               Toast.LENGTH_SHORT
                           ).show()
                       }
                   )
               } else {
                   Toast.makeText(
                       context,
                       "Title or Description can't be empty!",
                       Toast.LENGTH_SHORT
                   ).show()
               }

            }
        )
    }
}



