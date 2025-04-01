package com.example.week5recyclerviewroom;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Name.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NameDao nameDao();
}
