package com.example.notes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notes.R;
import com.example.notes.model.NotesModel;
import com.example.notes.viewModel.NotesViewModel;

import java.util.List;


public class CompletedFragment extends Fragment {


    NotesViewModel notesViewModel;

    RecyclerView recyclerView;

    List<NotesModel> list1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_completed, container, false);

       recyclerView = view.findViewById(R.id.fragment_completed_recycleview);
       notesViewModel=new ViewModelProvider(this).get(NotesViewModel.class);


       notesViewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<List<NotesModel>>() {
           @Override
           public void onChanged(List<NotesModel> notesModels) {



           }
       });



       return  view;
    }
}