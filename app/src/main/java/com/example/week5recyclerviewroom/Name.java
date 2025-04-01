package com.example.week5recyclerviewroom;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "names")
public class Name {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameText;

    public Name(String nameText) {
        this.nameText = nameText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }
}
