package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.databinding.ActivityAddNoteBinding;

public class AddNoteActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 0;
    ActivityAddNoteBinding activityAddNoteBinding;

    EditText title;
    EditText description;

    ImageView cancel;
   ImageView add;

   ImageView img;

   Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAddNoteBinding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(activityAddNoteBinding.getRoot());


        title = activityAddNoteBinding.activityAddNoteTitle;
        description= activityAddNoteBinding.activityAddNoteDescription;
        add= activityAddNoteBinding.activityAddNoteTrue;
        cancel= activityAddNoteBinding.activityAddNoteFalse;
        img = activityAddNoteBinding.activityAddNotesImg;

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(title.getText()) &&  !TextUtils.isEmpty(description.getText()) ) {
                    Intent i = new Intent();
                    i.putExtra("title", title.getText().toString());
                    i.putExtra("desc", description.getText().toString());
                    if (imgUri != null) {
                        i.putExtra("img", imgUri.toString());
                    }
                    setResult(RESULT_OK, i); // this means that this intent will take this data to from where this intent was called from
                    finish();

                }else {
                    Toast.makeText(AddNoteActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                startActivityForResult(galleryIntent,GALLERY_CODE);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK && data!=null){

            imgUri= data.getData();
            img.setImageURI(imgUri);
        }

    }
}