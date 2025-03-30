package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.project2.databinding.ActivityPlaceHoldBinding;
import com.example.project2.databinding.DialogLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class PlaceHoldActivity extends AppCompatActivity {
    private ActivityPlaceHoldBinding binding;
    private LibraryDatabase db;
    private BookDao bookDao;
    private UserDao userDao;
    private TransactionDao transactionDao;
    private List<Book> availableBooks;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceHoldBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LibraryDatabase.getInstance(getApplicationContext());
        bookDao = db.bookDao();
        userDao = db.userDao();
        transactionDao = db.transactionDao();

        setupGenreSpinner();
        setupRecyclerView();
        setupPlaceHoldButton();
    }

    private void setupGenreSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genreSpinner.setAdapter(adapter);
        binding.genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGenre = parent.getItemAtPosition(position).toString();
                loadBooksForGenre(selectedGenre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupRecyclerView() {
        binding.booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(new ArrayList<>());
        binding.booksRecyclerView.setAdapter(bookAdapter);
    }

    private void loadBooksForGenre(String genre) {
        availableBooks = bookDao.getBooksByGenre(genre);
        if (availableBooks.isEmpty()) {
            Toast.makeText(this, "No books available for this genre", Toast.LENGTH_SHORT).show();
        }
        bookAdapter.updateBooks(availableBooks);
    }

    private void setupPlaceHoldButton() {
        binding.PHButton.setOnClickListener(v -> {
            Book selectedBook = bookAdapter.getSelectedBook();
            if (selectedBook == null) {
                Toast.makeText(this, "Please select a book", Toast.LENGTH_SHORT).show();
                return;
            }
            showLoginDialog(selectedBook);
        });
    }

    private void showLoginDialog(Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogLoginBinding dialogBinding = DialogLoginBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot())
                .setTitle("Login")
                .setPositiveButton("OK", (dialog, which) -> {
                    String username = dialogBinding.usernameEditText.getText().toString();
                    String password = dialogBinding.passwordEditText.getText().toString();
                    validateUserAndPlaceHold(username, password, book);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void validateUserAndPlaceHold(String username, String password, Book book) {
        User user = userDao.validateUser(username, password);
        if (user != null) {
            int reservationNumber = transactionDao.count() + 1;
            Transaction transaction = new Transaction(username, "Place Hold", book.getBookId());
            transactionDao.addTransaction(transaction);

            showConfirmationDialog(username, book.getBookTitle(), reservationNumber);
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void showConfirmationDialog(String username, String bookTitle, int reservationNumber) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Username: " + username + "\nBook Title: " + bookTitle + "\nReservation Number: " + reservationNumber)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }
}
