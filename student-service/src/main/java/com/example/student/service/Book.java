package com.example.student.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Book {
    private int bookId;
    private String name;
    private String writer;
    private int noOfCopiesLeft;
    private int studentId;
}
