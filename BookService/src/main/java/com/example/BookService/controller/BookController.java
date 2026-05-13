package com.example.BookService.controller;

import com.example.BookService.model.Book;
import com.example.BookService.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BookController {
    @Autowired
    BookService bookService;
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }
    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id){
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    }
}
