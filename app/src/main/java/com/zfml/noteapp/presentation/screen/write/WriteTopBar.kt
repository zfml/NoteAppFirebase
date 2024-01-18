package com.zfml.noteapp.presentation.screen.write

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.SavedStateHandle
import com.zfml.noteapp.domain.model.Note
import com.zfml.noteapp.util.toLocalDate
import com.zfml.noteapp.util.toLocalDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteTopBar(
    createdDate: Long?,
    onDateChange: () -> Unit,
    onDeleteNote: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val formattedDate = remember(key1 = createdDate) {
        DateTimeFormatter
            .ofPattern("dd MMM yyyy")
            .format(createdDate?.toLocalDateTime() ?: LocalDateTime.now())
    }
    TopAppBar(
        title = {
            Text(
                text = formattedDate
                ,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateToHome) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
            }
        },
        actions = {
            if(createdDate != null) {
                IconButton(onClick = onDeleteNote) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Icon")
                }
            }


        }
    )
}