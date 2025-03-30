package com.example.project2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class, Transaction.class, User.class}, version=1, exportSchema = false)
public abstract class LibraryDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
    public abstract UserDao userDao();

    public abstract TransactionDao transactionDao();
    private static LibraryDatabase sInstance;

    public static synchronized LibraryDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    LibraryDatabase.class, "library.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }

    public void populateInitialData() {
        if (bookDao().count() == 0 && userDao().count() == 0){
            runInTransaction(() ->{
                // Here is where I populate books
                bookDao().addBook(new Book("Meditations", "Marcus Aurelius", "Self-Help"));
                bookDao().addBook(new Book("Letters to a Young Poet", "Rainer Maria Rilke", "Self-Help"));
                bookDao().addBook(new Book("Circe", "Madeline Miller", "Historical Fantasy"));
                bookDao().addBook(new Book("Reusable Software", "Bertrand Meyer", "Computer Science"));
                bookDao().addBook(new Book("Intro to Machine Learning", "Anita Faul", "Computer Science"));

                // Here is where I populate users
                userDao().addUser(new User("sShlaer", "oop"));
                userDao().addUser(new User("bMeyer", "eiffel"));
                userDao().addUser(new User("shirleyBee", "carmel2Chicago"));
                userDao().addUser(new User("!admin2", "!admin2"));
            });
        }
    }

}
