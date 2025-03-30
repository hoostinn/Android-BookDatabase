package com.example.project2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactionBank")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private int reservation_number;

    @ColumnInfo(name ="username")
    private String username;

    @ColumnInfo(name = "transaction_type")
    private String transactionType;

    @ColumnInfo(name = "book_id")
    private int bookId;

    public Transaction(String username, String transactionType, int bookId) {
        this.username = username;
        this.transactionType = transactionType;
        this.bookId = bookId;
    }

    public int getReservation_number() {
        return reservation_number;
    }

    public void setReservation_number(int reservation_number) {
        this.reservation_number = reservation_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
