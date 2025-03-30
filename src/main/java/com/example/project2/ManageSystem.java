package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.project2.databinding.ActivityManageSystemBinding;
import com.example.project2.databinding.DialogAddBookBinding;
import com.example.project2.databinding.DialogLoginBinding;

import java.util.List;

public class ManageSystem extends AppCompatActivity {
    private ActivityManageSystemBinding binding;
    private LibraryDatabase db;
    private UserDao userDao;
    private TransactionDao transactionDao;
    private BookDao bookDao;
    private TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageSystemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LibraryDatabase.getInstance(getApplicationContext());
        userDao = db.userDao();
        transactionDao = db.transactionDao();
        bookDao = db.bookDao();

        showLoginDialog();
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogLoginBinding dialogBinding = DialogLoginBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot())
                .setTitle("Librarian Login")
                .setPositiveButton("Login", (dialog, which) -> {
                    String username = dialogBinding.usernameEditText.getText().toString();
                    String password = dialogBinding.passwordEditText.getText().toString();
                    validateLibrarian(username, password);
                })
                .setNegativeButton("Cancel", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void validateLibrarian(String username, String password) {
        if ("!admin2".equals(username) && "!admin2".equals(password)) {
            displayTransactionLogs();
        } else {
            Toast.makeText(this, "Invalid librarian credentials", Toast.LENGTH_SHORT).show();
            showLoginDialog();
        }
    }

    private void displayTransactionLogs() {
        List<Transaction> transactions = transactionDao.getAllTransactions();
        transactionAdapter = new TransactionAdapter(transactions);
        binding.transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionRecyclerView.setAdapter(transactionAdapter);

        binding.okButton.setOnClickListener(v -> promptAddNewBook());
    }

    private void promptAddNewBook() {
        new AlertDialog.Builder(this)
                .setTitle("Add New Book")
                .setMessage("Do you want to add a new book?")
                .setPositiveButton("Yes", (dialog, which) -> showAddBookDialog())
                .setNegativeButton("No", (dialog, which) -> finish())
                .show();
    }

    private void showAddBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogAddBookBinding dialogBinding = DialogAddBookBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot())
                .setTitle("Add New Book")
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = dialogBinding.titleEditText.getText().toString();
                    String author = dialogBinding.authorEditText.getText().toString();
                    String genre = dialogBinding.genreEditText.getText().toString();
                    addNewBook(title, author, genre);
                })
                .setNegativeButton("Cancel", (dialog, which) -> finish())
                .show();
    }

    private void addNewBook(String title, String author, String genre) {
        if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Book newBook = new Book(title, author, genre);
        bookDao.addBook(newBook);
        Toast.makeText(this, "New book added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
