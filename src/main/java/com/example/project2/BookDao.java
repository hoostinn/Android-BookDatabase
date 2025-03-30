package com.example.project2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void addBook(Book book);

    @Update
    public void updateBook(Book book);

    @Query("select count(*) from bookBank")
    int count();

    @Query("select * from bookBank")
    List<Book> getAllBook();

    @Query("select * from bookBank WHERE genre = :genre")
    List<Book> getBooksByGenre(String genre);

    @Delete
    void delete(Book book);
}
