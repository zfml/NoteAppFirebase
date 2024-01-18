package com.zfml.noteapp.presentation.screen.write


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zfml.noteapp.presentation.components.TransparentHintTextField

@Composable
fun WriteContent(
    padding: PaddingValues,
    noteTitle: State<NoteTextFieldState>,
    noteDescription: State<NoteTextFieldState>,
    onTitleChanged: (String) -> Unit,
    onTitleFocusChanged: (FocusState) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDescriptionFocusChanged: (FocusState) -> Unit
) {
   Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(padding)
           .padding(8.dp)
   ){
       Spacer(modifier = Modifier.height(16.dp))
       TransparentHintTextField(
           hint = noteTitle.value.hint,
           text = noteTitle.value.text,
           onValueChange = onTitleChanged,
           onFocusChange = onTitleFocusChanged,
           isHintVisible = noteTitle.value.isHintVisible,
           singleLine = true,
           textStyle = TextStyle(
               fontSize = MaterialTheme.typography.titleLarge.fontSize
           )
       )
       Spacer(modifier = Modifier.height(16.dp))
       TransparentHintTextField(
           modifier = Modifier
               .fillMaxHeight(),
           hint = noteDescription.value.hint,
           text = noteDescription.value.text,
           onValueChange = onDescriptionChanged,
           onFocusChange = onDescriptionFocusChanged,
           isHintVisible = noteDescription.value.isHintVisible,
           textStyle = TextStyle(
               fontSize = MaterialTheme.typography.bodyLarge.fontSize
           )
       )
   }

}
@Preview
@Composable
fun WriteContentPreview() {
}
