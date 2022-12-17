package com.example.app_databinding_recyclerview_room.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.app_databinding_recyclerview_room.model.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void add(Student student);

    @Update
    void udpate(Student student);

    @Delete
    void delete(Student student);

    @Query("select id, name, age from student")
    LiveData<List<Student>> queryAll();

    @Query("select id, name, age from student where id = :id")
    Student getById(int id);
}