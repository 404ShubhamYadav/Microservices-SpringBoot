package com.example.BookService.controller;

import com.example.BookService.model.Book;
import com.example.BookService.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*

 * ANNOTATIONS USED:
 * @WebMvcTest     → loads ONLY web layer (controllers)
 *                   does NOT load Service, Repository, DB
 *                   does NOT start Tomcat on port 8080
 *
 * @MockBean       → creates a FAKE service bean
 *                   registered in Spring context
 *                   we control what it returns
 *                   NOTE: use @MockBean for Spring Boot 3.x
 *                         use @MockitoBean for Spring Boot 4.x
 *
 * MockMvc         → simulates HTTP requests (GET, POST etc)
 *                   like Postman but in code
 *                   no real server needed
 *
 * ObjectMapper    → converts Java object → JSON string
 *                   needed for sending request body
 */

@WebMvcTest(BookController.class)
// loads only BookController in Spring context
// BookService is NOT loaded (we will mock it)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;
    /*
     * MockMvc is the main tool for controller testing
     * It simulates HTTP calls without starting real server
     * Think of it as a virtual Postman inside your test
     */

    @MockBean
    BookService bookService;
    /*
     * @MockBean creates a FAKE BookService
     * and registers it in Spring context
     * so BookController can @Autowired it normally
     */

    @Autowired
    ObjectMapper objectMapper;
    /*
     * ObjectMapper converts Java object to JSON string
     * Example:
     * Book{name="Java", writer="James"}
     * → {"name":"Java","writer":"James"}
     *
     * Needed because HTTP request body must be JSON string
     * not a Java object
     */
    @Test
    public void should_ReturnCreated_When_ValidBookProvided() throws Exception {

        Book inputBook = Book.builder()
                .name("Java Programming")
                .writer("James Gosling")
                .noOfCopiesLeft(10)
                .studentId(1)
                .build();

        Book savedBook = Book.builder()
                .bookId(1)
                .name("Java Programming")
                .writer("James Gosling")
                .noOfCopiesLeft(10)
                .studentId(1)
                .build();

        Mockito.when(bookService.addBook(inputBook))
                .thenReturn(savedBook);

        // ---- WHEN + THEN (perform request and check response) ----

        mockMvc.perform(
                        post("/add")
                                /*
                                 * post("/add") = simulate POST request to /add endpoint
                                 * same as: POST http://localhost:8080/add in Postman
                                 */
                                .contentType(MediaType.APPLICATION_JSON)
                                /*
                                 * tells server we are sending JSON
                                 * same as setting Content-Type: application/json
                                 * in Postman headers
                                 */
                                .content(objectMapper.writeValueAsString(inputBook))
                        /*
                         * objectMapper.writeValueAsString(inputBook)
                         * converts inputBook Java object to JSON string:
                         * {"name":"Java Programming","writer":"James Gosling",
                         *  "noOfCopiesLeft":10,"studentId":1}
                         *
                         * this JSON string goes in request body
                         * same as writing JSON in Postman body tab
                         */
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.name").value("Java Programming"))
                .andExpect(jsonPath("$.writer").value("James Gosling"))
                .andExpect(jsonPath("$.noOfCopiesLeft").value(10));
    }

    // ============================================================
    // TEST 2 - GET /getBook/{id} - Happy Path
    // ============================================================
    @Test
    public void should_ReturnBook_When_ValidIdProvided() throws Exception {

        // ---- GIVEN ----

        Book book = Book.builder()
                .bookId(1)
                .name("Java Programming")
                .writer("James Gosling")
                .noOfCopiesLeft(10)
                .studentId(1)
                .build();

        /*
         * when getBook(1) is called on fake service
         * return book object
         * do NOT hit real database
         */
        Mockito.when(bookService.getBook(1))
                .thenReturn(book);

        // ---- WHEN + THEN ----

        mockMvc.perform(
                        get("/getBook/1")
                                /*
                                 * get("/getBook/1") = simulate GET request
                                 * same as: GET http://localhost:8080/getBook/1
                                 * 1 is the path variable {id}
                                 */
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                // checks HTTP status is 200 OK
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.name").value("Java Programming"))
                .andExpect(jsonPath("$.writer").value("James Gosling"))
                .andExpect(jsonPath("$.noOfCopiesLeft").value(10));
    }

    // ============================================================
    // TEST 3 - GET /getBook/{id} - Sad Path (Exception)
    // ============================================================
    @Test
    public void should_ReturnInternalServerError_When_InvalidIdProvided() throws Exception {

        // ---- GIVEN ----
        Mockito.when(bookService.getBook(999))
                .thenThrow(new RuntimeException("Book not found with id: 999"));

        // ---- WHEN + THEN ----

        mockMvc.perform(
                        get("/getBook/999")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());

    }
}