package com.example.week5recyclerviewroom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NameDao {

    @Insert
    void insert(Name name);

    @Query("SELECT * FROM names")
    List<Name> getAllNames();

    @Update
    void update(Name name);

    @Delete
    void delete(Name name);

}
