package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.notes.databinding.ActivityUpdateBinding;
import com.example.notes.model.NotesModel;
import com.example.notes.viewModel.NotesViewModel;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding activityUpdateBinding;


    EditText titleEt;
    EditText descriptionEt;

    ImageView imgV;

    ImageView iconV;

    NotesViewModel notesViewModel;


    ImageView add;
    ImageView cancel;


    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateBinding=ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(activityUpdateBinding.getRoot());

         titleEt = activityUpdateBinding.activityUpdateTitle;
         descriptionEt = activityUpdateBinding.activityUpdateDescription;
         imgV = activityUpdateBinding.activityUpdateImg;
         iconV= activityUpdateBinding.icon;
         add= activityUpdateBinding.activityUpdateAdd;
         cancel= activityUpdateBinding.activityUpdateCancel;

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String description = i.getStringExtra("description");
        String img = i.getStringExtra("img");



        titleEt.setText(title);
        descriptionEt.setText(description);

        if(img!=null) {
            Uri uri = Uri.parse(img);
            iconV.setVisibility(View.GONE);
            Glide.with(UpdateActivity.this).load(uri).into(imgV);
            imgUri = uri;
        }

        imgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,69);

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(titleEt.getText()) && !TextUtils.isEmpty(descriptionEt.getText())) {

                    String updatedTitle = titleEt.getText().toString();
                    String updatedDescription = descriptionEt.getText().toString();

                    String updatedUri= null;

                    if(imgUri!=null){
                        updatedUri = String.valueOf(imgUri);
                    }

                    Intent intent = new Intent();
                    intent.putExtra("titleUpdated",updatedTitle);
                    intent.putExtra("descriptionUpdated",updatedDescription);
                    intent.putExtra("updatedUri",updatedUri);
                    intent.putExtra("id",i.getIntExtra("id",-1));  // whenever ur getting a value use
                    // getExtra of particular type stringExtra ,InExtra, or BooleanExtra
                    // or just using StringExtra to get int or a boolean and later converting them into string may gave an exception
                    intent.putExtra("isSelected",i.getBooleanExtra("isSelected",false));
                    setResult(RESULT_OK,intent);
                    finish();


                }else {
                    Toast.makeText(UpdateActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==69 && data!=null){

            imgUri=data.getData();
            imgV.setImageURI(imgUri);
        }

    }
}