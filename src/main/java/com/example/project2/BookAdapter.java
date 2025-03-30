package com.example.project2;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.databinding.ItemBookBinding;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books;
    private Book selectedBook;

    // The constructor to initialize data
    public BookAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void updateBooks(List<Book> newBooks) {
        books = newBooks;
        notifyDataSetChanged();
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private ItemBookBinding binding;

        BookViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                selectedBook = books.get(getAdapterPosition());
                notifyDataSetChanged();
            });
        }

        void bind(Book book) {
            binding.bookTitleTextView.setText(book.getBookTitle());
            binding.bookAuthorTextView.setText(book.getAuthor());
        }
    }
}

