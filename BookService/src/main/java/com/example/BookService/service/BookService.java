package com.example.BookService.service;

import com.example.BookService.model.Book;
import com.example.BookService.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    public Book addBook(Book book) {
       return bookRepository.save(book);
    }

    public Book getBook(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
}
