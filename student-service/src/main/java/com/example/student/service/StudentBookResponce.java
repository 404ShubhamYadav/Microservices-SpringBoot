package com.example.student.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentBookResponce {
    private int studentId;
    private String name;
    private String email;
    private Book book;
}
