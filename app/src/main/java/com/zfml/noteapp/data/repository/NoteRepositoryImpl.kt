package com.zfml.noteapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.zfml.noteapp.domain.model.Note
import com.zfml.noteapp.domain.repository.NoteRepository
import com.zfml.noteapp.domain.model.Response
import com.zfml.noteapp.util.Constants.CREATED_DATE
import com.zfml.noteapp.util.Constants.NOTES_COLLECTION
import com.zfml.noteapp.util.toLocalDate
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Exception

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val notesRef: CollectionReference,
) : NoteRepository {
    private val userId = Firebase.auth.currentUser?.uid

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllNote() = callbackFlow {


        val snapshotListener = notesRef.document(userId!!)
            .collection(NOTES_COLLECTION)
            .orderBy(CREATED_DATE , Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                val notesResponse = if (snapshot != null) {
                    Response.Loading
                    val notes = snapshot.toObjects(Note::class.java)
                    Response.Success(
                        notes.groupBy { note ->
                            note.createdDate.toLocalDate()
                        }
                    )
                } else {
                    Response.Error(error)
                }
                trySend(notesResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun getNoteById(noteId: String): Response<Note> = try {
        val noteDocument =
            notesRef.document(userId!!).collection(NOTES_COLLECTION).document(noteId).get().await()
        val note = noteDocument.toObject<Note>() ?: Note()
        Response.Success(note)
    } catch (e: Exception) {
        Response.Error(e)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addNote(note: Note): Response<Boolean> = try {
        val id = notesRef.document().id
        notesRef.document(userId!!).collection(NOTES_COLLECTION).document(id)
            .set(note.copy(id = id)).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Error(e)
    }

    override suspend fun updateNote(note: Note): Response<Boolean> = try {
         notesRef.document(userId!!).collection(NOTES_COLLECTION).document(note.id).set(note).await()
                    Response.Success(true)
                } catch (e: Exception) {
                    Response.Error(e)
                }
    override suspend fun deleteNote(noteId: String): Response<Boolean> = try {
        notesRef.document(userId!!).collection(NOTES_COLLECTION).document(noteId).delete().await()
        Response.Success(true)
    } catch (e : Exception) {
        Response.Error(e)
    }


}