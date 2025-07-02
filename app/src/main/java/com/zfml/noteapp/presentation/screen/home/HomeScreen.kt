package com.zfml.noteapp.presentation.screen.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zfml.noteapp.R
import com.zfml.noteapp.presentation.components.EmptyPage

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    userName: String,
    userImage: String,
    notesUiState: NotesUiState,
    onSignOutClicked: () -> Unit,
    openDrawerClicked: () -> Unit,
    onNavigateToWrite: () -> Unit,
    onNavigateToWriteWithArg: (String) -> Unit
     ) {
    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()


    NavigationDrawer(
        drawerState = drawerState ,
        userName = userName ,
        userImage = userImage,
        onSignOutClicked = onSignOutClicked,
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
            ,
            topBar = {
                HomeTopBar(
                    openDrawerClicked = openDrawerClicked,
                    topAppBarScrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onNavigateToWrite) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Icon"
                    )
                }
            },
            content = {padding ->

                if(notesUiState.error.isNotEmpty()) {
                    EmptyPage(
                       title = notesUiState.error
                    )
                }
                if(notesUiState.notes.isNotEmpty()) {
                    HomeContent(
                        onClick = onNavigateToWriteWithArg,
                        padding = padding,
                        notes = notesUiState.notes
                    )
                }else {
                    EmptyScreen()
                }

            }
        )
    }

    }

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    userName: String?,
    userImage: String?,
    onSignOutClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
             ModalDrawerSheet(
                 drawerShape = Shapes().small,
                 content = {
                     Box(
                         contentAlignment = Alignment.Center
                     ) {
                         Column(
                             modifier = Modifier
                                 .padding(16.dp)
                             ,
                         ) {
                             AsyncImage(
                                 modifier = Modifier
                                     .clip(CircleShape)
                                     .size(64.dp)
                                 ,
                                 model = userImage,
                                 contentDescription = null,
                                 contentScale = ContentScale.Crop
                             )
                             Spacer(modifier = Modifier.height(8.dp))
                             Text(
                                 text  = userName ?: "",
                                 fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                 fontWeight = FontWeight.Bold
                             )
                         }

                     }

                     NavigationDrawerItem(
                         label = {
                           Row(
                               modifier = Modifier
                                   .fillMaxWidth(),
                               verticalAlignment = Alignment.CenterVertically
                           ){
                               Image(
                                   painter = painterResource(id = R.drawable.ic_google_logo),
                                   contentDescription = "Google Logo"
                               )
                               Spacer(modifier = Modifier.width(8.dp))
                               Text(
                                   text ="Sign Out"
                               )
                           }
                         },
                         selected = false,
                         onClick = onSignOutClicked)
                 }
             )
        },
        content = content
    )

}