package com.example.notes.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.notes.db.DataDao;
import com.example.notes.db.NotesDatabase;
import com.example.notes.model.NotesModel;

import java.util.List;
import java.util.concurrent.Executors;

public class Repository {

    NotesDatabase notesDatabase;

   public DataDao dataDao;
    LiveData<List<NotesModel>> listLiveData;

    public Repository(Application application) {

        notesDatabase = NotesDatabase.getInstance(application);
        dataDao= notesDatabase.getDataDao();
        listLiveData= dataDao.getAllNotes();

    }

    public LiveData<List<NotesModel>> getListLiveData(){
        return  listLiveData;
    }

public  void insertNote(NotesModel notesModel){

    Executors.newSingleThreadExecutor().execute(new Runnable() {
        @Override
        public void run() {
            dataDao.insertNote(notesModel);
        }
    });

}

public  void deleteData(NotesModel notesModel){
      Executors.newSingleThreadExecutor().execute(new Runnable() {
          @Override
          public void run() {
              dataDao.deleteNote(notesModel);
          }
      });
}

    public  void updateData(NotesModel notesModel){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dataDao.updateNote(notesModel);
            }
        });
    }


    public  void deleteAllNotes(){

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dataDao.deleteAllNotes();
            }
        });

    }

}
