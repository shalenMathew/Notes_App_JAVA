package com.example.notes;

import android.Manifest;
;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.notes.databinding.ActivityMainBinding;
import com.example.notes.db.NotesDatabase;
import com.example.notes.fragments.CompletedFragment;
import com.example.notes.fragments.StarredFragment;
import com.example.notes.fragments.NotesFragment;
import com.example.notes.model.NotesModel;
import com.example.notes.viewModel.NotesViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    // Room + Mvvm + dependency injection + view binding


    TabLayout tabLayout;
    ViewPager2 viewPager;

    ViewPagerAdapter viewPagerAdapter;

    ActivityMainBinding activityMainBinding;

    NotesViewModel notesViewModel;

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 123;


    private  final String[] title={"Notes","Starred","Completed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());


        notesViewModel= new ViewModelProvider(this).get(NotesViewModel.class);

        tabLayout= activityMainBinding.tabLayout;
        viewPager = activityMainBinding.viewPager;
        viewPagerAdapter= new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        activityMainBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddNoteActivity();
            }
        });


       if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
               == PackageManager.PERMISSION_GRANTED){
         

        }else {
           ActivityCompat.requestPermissions(MainActivity.this,
                   new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                   REQUEST_READ_EXTERNAL_STORAGE);
       }

        new TabLayoutMediator(tabLayout,viewPager,((tab, position) -> tab.setText(title[position]))).attach();



    }

    public void startAddNoteActivity(){

        Intent i = new Intent(MainActivity.this,AddNoteActivity.class);
        startActivityForResult(i,1); // this will go to AddNoteActivity and return back with the data

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start AddNoteActivity

            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission denied. Cannot access storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1 && data!=null ){

            String title = data.getStringExtra("title");
            String description = data.getStringExtra("desc");
            String img = data.getStringExtra("img");
            NotesModel notesModel = new NotesModel(title,description,img,false);
            notesViewModel.insertData(notesModel);
            Toast.makeText(this, "Notes created", Toast.LENGTH_SHORT).show();
        } else if (requestCode==49 && data!=null && resultCode==RESULT_OK) {

            String updatedTitle = data.getStringExtra("titleUpdated");
            String updatedDescription = data.getStringExtra("descriptionUpdated");
            String updatedUri = data.getStringExtra("updatedUri");
            int id = data.getIntExtra("id",-1);
            boolean isSelected = data.getBooleanExtra("isSelected",false);



           NotesModel notesModel = new NotesModel(updatedTitle,updatedDescription,updatedUri,isSelected);

           notesModel.setId(id); // --. this will let the room know which notemodel obj should it update

           notesViewModel.updateData(notesModel);

        }
    }


    public  void deleteNotes(NotesModel notesModel){
        notesViewModel.deleteData(notesModel);
    }


    public  void  updateNotes(NotesModel notesModel){
        notesViewModel.updateData(notesModel);
    }



    public  class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {


            switch (position){
                case  0: return new NotesFragment();

                case 1:return new StarredFragment();

                case 2: return new CompletedFragment();
            }

            return  new NotesFragment();

        }

        @Override
        public int getItemCount() {
            return title.length;
        }
    }

}