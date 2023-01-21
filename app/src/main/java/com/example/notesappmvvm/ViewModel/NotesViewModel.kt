package com.example.notesappmvvm.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesappmvvm.Database.NotesDatabase
import com.example.notesappmvvm.Model.Notes
import com.example.notesappmvvm.Repository.NotesRepository
import java.net.IDN

class NotesViewModel(application: Application): AndroidViewModel(application) {

    val repository:NotesRepository

    init {
        val dao=NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }
    fun addNotes(notes: Notes){
        repository.insertNotes(notes)
    }

    fun getNotes():LiveData<List<Notes>> = repository.getallNotes()

    fun deleteNotes(id: Int){
        repository.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        repository.updateNotes(notes)
    }


}