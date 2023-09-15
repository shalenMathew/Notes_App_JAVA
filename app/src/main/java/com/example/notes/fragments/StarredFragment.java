package com.example.notes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.adapter.CardItemAdapter;
import com.example.notes.model.NotesModel;
import com.example.notes.viewModel.NotesViewModel;

import java.util.ArrayList;
import java.util.List;


public class StarredFragment extends Fragment {


    RecyclerView recyclerView;

    CardItemAdapter adapter;

    NotesViewModel notesViewModel;


    List<NotesModel> starredList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.fragment_starred, container, false);


      notesViewModel= new ViewModelProvider(this).get(NotesViewModel.class);

      starredList = new ArrayList<>();


      notesViewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<List<NotesModel>>() {
          @Override
          public void onChanged(List<NotesModel> notesModels) {


              starredList.clear();

              for(NotesModel obj: notesModels){

                  if(obj.isStarSelected()){
                      starredList.add(obj);
                  }

              }
              adapter.notifyDataSetChanged();
          }
      });


      recyclerView = view.findViewById(R.id.important_recycleview);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CardItemAdapter(getContext(),starredList,notesViewModel);
        recyclerView.setAdapter(adapter);

      return  view;
    }

}