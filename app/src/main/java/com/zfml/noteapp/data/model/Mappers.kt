package com.zfml.noteapp.data.model

import com.zfml.noteapp.domain.model.Note

fun NoteDto.toNote(): Note = Note(
    id = id,
    title = title,
    description = description,
    color = color,
    createdDate = createdDate
)

fun  Note.toNoteDto(): NoteDto = NoteDto(
    id = id,
    title = title,
    description = description,
    color = color,
    createdDate = createdDate
)