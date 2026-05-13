package com.example.student.service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private long contactNo;
//    private List<String> books= new ArrayList<>();
}
