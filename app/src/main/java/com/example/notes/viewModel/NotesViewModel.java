package com.example.notes.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.notes.model.NotesModel;
import com.example.notes.repository.Repository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {


   public Repository repository;

   public LiveData<List<NotesModel>> listLiveData;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        listLiveData= repository.getListLiveData();
    }

    public LiveData<List<NotesModel>> getLiveData(){
        return  listLiveData;
    }

    public void insertData(NotesModel notesModel){
        repository.insertNote(notesModel);
    }


   public void deleteData(NotesModel notesModel){
        repository.deleteData(notesModel);
    }

  public   void updateData(NotesModel notesModel){
        repository.updateData(notesModel);
    }


   public void deleteAllData(){
        repository.deleteAllNotes();
    }

}
