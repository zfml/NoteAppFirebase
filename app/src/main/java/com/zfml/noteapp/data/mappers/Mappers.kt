package com.zfml.noteapp.data.mappers

import com.zfml.noteapp.data.model.NoteDto
import com.zfml.noteapp.data.model.UserDto
import com.zfml.noteapp.domain.model.Note
import com.zfml.noteapp.domain.model.User

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

fun User.toUserDto(): UserDto = UserDto(
    uid = uid,
    email = email,
    name = name
)

fun UserDto.toUser(): User = User(
    uid = uid,
    email = email,
    name = name
)