package com.example.notes.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.model.NotesModel;

import java.util.List;

@Dao
public interface DataDao {


    @Insert
    void insertNote(NotesModel notesModel);

    @Update
    void updateNote(NotesModel notesModel);


    @Delete
    void deleteNote(NotesModel notesModel);


    @Query(" DELETE FROM notes_table")
    void deleteAllNotes();

    @Query(" SELECT * FROM notes_table")
    LiveData<List<NotesModel>> getAllNotes();


}
