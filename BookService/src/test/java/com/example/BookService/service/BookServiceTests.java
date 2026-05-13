package com.example.BookService.service;

import com.example.BookService.model.Book;
import com.example.BookService.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookService bookService;

    @Test
    public void should_ReturnSavedBook_When_ValidBookProvided(){
        // given
        Book book = Book.builder()
                .name("Java Programming")
                .writer("James Gosling")
                .noOfCopiesLeft(10)
                .studentId(1)
                .build();

        Book savedBook = Book.builder()
                .bookId(1)           // DB generated ID
                .name("Java Programming")
                .writer("James Gosling")
                .noOfCopiesLeft(10)
                .studentId(1)
                .build();

        // when repository.save() is called
        // return savedBook (fake DB response)
        Mockito.when(bookRepository.save(book))
                .thenReturn(savedBook);

        // when
        Book result = bookService.addBook(book);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getBookId());
        Assertions.assertEquals("Java Programming", result.getName());
        Assertions.assertEquals("James Gosling", result.getWriter());

        // verify repository.save() was called exactly once
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    // ✅ Test 2 - getBook happy path
    @Test
    public void should_ReturnBook_When_ValidIdProvided() {
        // given
        Book book = Book.builder()
                .bookId(1)
                .name("Java Programming")
                .writer("James Gosling")
                .noOfCopiesLeft(10)
                .studentId(1)
                .build();

        // when repository.findById() is called with 1
        // return book wrapped in Optional
        Mockito.when(bookRepository.findById(1))
                .thenReturn(Optional.of(book));

        // when
        Book result = bookService.getBook(1);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getBookId());
        Assertions.assertEquals("Java Programming", result.getName());
        Assertions.assertEquals("James Gosling", result.getWriter());

        // verify repository.findById() was called exactly once
        Mockito.verify(bookRepository, Mockito.times(1)).findById(1);
    }

    // ✅ Test 3 - getBook sad path (exception)
    @Test
    void should_ThrowException_When_InvalidIdProvided() {
        // given
        // when repository.findById() is called with 999
        // return empty Optional (book not found)
        Mockito.when(bookRepository.findById(999))
                .thenReturn(Optional.empty());

        // when + then
        // service should throw RuntimeException
        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> bookService.getBook(999)
        );

        // verify exception message is correct
        Assertions.assertEquals(
                "Book not found with id: 999",
                exception.getMessage()
        );

        // verify repository.findById() was called exactly once
        Mockito.verify(bookRepository, Mockito.times(1)).findById(999);
    }

}
