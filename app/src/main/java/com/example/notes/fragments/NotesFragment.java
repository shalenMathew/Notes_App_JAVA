package com.example.notes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.adapter.CardItemAdapter;
import com.example.notes.model.NotesModel;
import com.example.notes.viewModel.NotesViewModel;

import java.util.List;


public class NotesFragment extends Fragment {


RecyclerView recyclerView;

CardItemAdapter cardItemAdapter;

List<NotesModel> list;

NotesViewModel notesViewModel;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_notes, container, false);



       recyclerView = view.findViewById(R.id.recycle_view);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

       notesViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<NotesModel>>() {
           @Override
           public void onChanged(List<NotesModel> notesModels) {


               cardItemAdapter = new CardItemAdapter(getContext(),notesModels,notesViewModel);
               recyclerView.setAdapter(cardItemAdapter);

           }
       });

       return  view;
    }


}