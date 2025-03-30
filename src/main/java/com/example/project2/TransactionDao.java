package com.example.project2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void addTransaction(Transaction transaction);

    @Update
    public void updateTransaction(Transaction transaction);

    @Query("select count(*) from transactionBank")
    int count();

    @Query("select * from transactionBank")
    List<Transaction> getAllTransactions();

    @Query("select * from transactionBank where transaction_type = :transactionType")
    List<Transaction> getTransactionsByType(String transactionType);

    @Query("select * from transactionBank where book_id = :bookId")
    Transaction getTransactionsByBookId(int bookId);

    @Delete
    void delete(Transaction transaction);
}
