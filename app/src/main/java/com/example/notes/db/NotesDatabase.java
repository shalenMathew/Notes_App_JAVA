package com.example.notes.db;


import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notes.MainActivity;
//import com.example.notes.dependencyInjection.App;
import com.example.notes.model.NotesModel;

import javax.inject.Inject;


@Database(entities = NotesModel.class,version = 4)
public abstract class NotesDatabase extends RoomDatabase {

static NotesDatabase instance;

public abstract DataDao getDataDao();

public static synchronized NotesDatabase getInstance(Context context){

    if(instance==null){

        instance = Room.databaseBuilder(context,
                        NotesDatabase.class,
                        "notes_database")
                .fallbackToDestructiveMigration()
                .build();


    }

    return  instance;
}
}
