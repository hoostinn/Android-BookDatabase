package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LibraryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LibraryDatabase.getInstance(this);
        db.populateInitialData();

        binding.CAButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
        });

        binding.PHButton.setOnClickListener(v ->  {
                Intent intent = new Intent(MainActivity.this, PlaceHoldActivity.class);
                startActivity(intent);
        });

        binding.navButton.setOnClickListener(v ->  {
            Intent intent = new Intent(MainActivity.this, ManageSystem.class);
            startActivity(intent);
        });

    }


}