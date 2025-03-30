package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.os.Bundle;
import android.widget.Toast;

import com.example.project2.databinding.ActivityCreateAccountBinding;

import java.util.List;

public class CreateAccount extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;
    private LibraryDatabase db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LibraryDatabase.getInstance(getApplicationContext());
        userDao = db.userDao();

        binding.CAButton.setOnClickListener(v -> createAccount ());
    }

    private void createAccount() {

        String username = binding.createUser.getText().toString().trim();
        String password = binding.createPass.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.equals("!admin2")) {
            Toast.makeText(this, "Username '!admin2' is reserved", Toast.LENGTH_SHORT).show();
            return;
        }

        List<User> existingUsers = userDao.getUserByUsername(username);
        if (!existingUsers.isEmpty()) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(username, password);
        userDao.addUser(newUser);

        TransactionDao transactionDao = db.transactionDao();
        Transaction transaction = new Transaction(username, "New Account", 0);
        transactionDao.addTransaction(transaction);

        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
        finish();

    }


}