package com.example.project2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookBank")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int bookId;

    @ColumnInfo(name ="book_title")
    private String bookTitle;

    @ColumnInfo(name ="author")
    private String Author;

    @ColumnInfo(name ="genre")
    private String Genre;

    public Book() {
    }

    public Book(String bookTitle, String author, String genre) {
        this.bookTitle = bookTitle;
        Author = author;
        Genre = genre;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}
