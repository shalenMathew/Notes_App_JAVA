package com.example.notes.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class NotesModel {




    @ColumnInfo(name = "notes_id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "notes_title")
    String title;

    @ColumnInfo(name = "notes_description")
    String description;


    @ColumnInfo(name = "notes_img")
    String img;

    @ColumnInfo(name = "notes_boolean")
    boolean isStarSelected=false;


    @Ignore
    public NotesModel() {
    }


    public NotesModel(String title, String description, String img, boolean isStarSelected) {
        this.title = title;
        this.description = description;
        this.img = img;
        this.isStarSelected = isStarSelected;
    }




    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public boolean isStarSelected() {
        return isStarSelected;
    }

    public void setStarSelected(boolean starSelected) {
        isStarSelected = starSelected;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
