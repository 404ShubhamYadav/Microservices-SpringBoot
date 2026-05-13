package com.example.BookService.repository;

import com.example.BookService.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepoTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    public void whenSaveBook_thenReturnValidBook(){
        Book book =Book.builder()
                .name("Bhagvat Geeta")
                .writer("Shree Krishna")
                .noOfCopiesLeft(921)
                .studentId(2)
                .build();

        Book savedBook = bookRepository.save(book);
        Assertions.assertNotNull(savedBook);
        Assertions.assertEquals("Bhagvat Geeta", savedBook.getName());
    }
    @Test
    public void should_ReturnBook_When_ValidIdProvided() {
        Book book = Book.builder()
                .name("Spring Boot")
                .writer("Rod Johnson")
                .noOfCopiesLeft(5)
                .studentId(2)
                .build();
        Book savedBook = bookRepository.save(book);

        var found = bookRepository.findById(savedBook.getBookId());
//        Optional<Book> found = bookRepository.findById(savedBook.getBookId());

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("Spring Boot", found.get().getName());
    }

    @Test
    public void shouldReturnEmpty_whenInvalidIdProvided(){
        var found = bookRepository.findById(999);
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    void should_ReturnAllBooks_When_MultipleBooksSaved() {
        Book book1 = Book.builder()
                .name("Java Programming")
                .writer("James Gosling")
                .noOfCopiesLeft(10)
                .studentId(1)
                .build();
        Book book2 = Book.builder()
                .name("Spring Boot")
                .writer("Rod Johnson")
                .noOfCopiesLeft(5)
                .studentId(2)
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);

        var allBooks = bookRepository.findAll();

        Assertions.assertFalse(allBooks.isEmpty());
        Assertions.assertTrue(allBooks.size() >= 2);
    }

    @Test
    void should_UpdateBook_When_ValidDataProvided() {
        Book book = Book.builder()
                .name("Java Programming")
                .writer("James Gosling")
                .noOfCopiesLeft(10)
                .studentId(1)
                .build();
        Book savedBook = bookRepository.save(book);

        savedBook.setNoOfCopiesLeft(5);
        Book updatedBook = bookRepository.save(savedBook);

        Assertions.assertEquals(5, updatedBook.getNoOfCopiesLeft());
    }

    @Test
    public void should_DeleteBook_When_ValidIdProvided() {
        Book book = Book.builder()
                .name("Java Programming")
                .writer("James Gosling")
                .noOfCopiesLeft(10)
                .studentId(1)
                .build();
        Book savedBook = bookRepository.save(book);

        bookRepository.deleteById(savedBook.getBookId());

        Assertions.assertFalse(bookRepository.findById(savedBook.getBookId()).isPresent());
    }

}
