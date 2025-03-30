package com.example.project2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void addUser(User user);

    @Update
    public void updateUser(User user);

    @Query("select count(*) from userBank")
    int count();

    @Query("select * from userBank")
    List<User> getAllUsers();

    @Query("select * from userBank where username is :username")
    List<User> getUserByUsername(String username);

    @Query("select * from userBank where username is :username and password = :password")
    User validateUser(String username, String password);

    @Delete
    void delete(User user);
}
