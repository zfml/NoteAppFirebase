package com.zfml.noteapp.presentation.components

import android.os.Build
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import com.zfml.noteapp.domain.model.Note
import com.zfml.noteapp.ui.theme.Elevation
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteHolder(
    note: Note,
    onClick: (String) -> Unit
) {

    val localDensity = LocalDensity.current
    var componentHeight by remember{ mutableStateOf(0.dp) }
    Row(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember{
                    MutableInteractionSource()
                }
            ) {
                onClick(note.id)
            }
    ) {
       Spacer(modifier = Modifier.width(14.dp))
       Surface(
           modifier = Modifier
               .width(2.dp)
               .height(componentHeight + 14.dp),
           tonalElevation = Elevation.Level1,
       ){}
       Spacer(modifier = Modifier.width(20.dp))
        Surface (
            modifier = Modifier
                .clip(Shapes().medium)
                .onGloballyPositioned {
                    componentHeight = with(localDensity){it.size.height.toDp()}
                }
            ,
            tonalElevation = Elevation.Level1
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = note.title ?: "",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        ),
                        maxLines = 1
                    )
                    Text(
                        modifier = Modifier.padding(14.dp),
                        text = SimpleDateFormat("hh:mm a",Locale.US)
                            .format(Date.from(Date(note.createdDate).toInstant()))
                        ,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = FontWeight.Light
                        ),
                        maxLines = 1
                    )
                }

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = note.description ?: "",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    ),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }


}

@Preview()
@Composable
fun NoteHolderPreview() {
//    NoteHolder(note = Note(title = "Piano ", description = "I am listening to the Piano", timestamp = Timestamp.now()), onClick = {})
}