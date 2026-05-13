package com.example.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    BookClient bookClient;
    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable("id") int id){
        return bookClient.getBook(id);
    }

    @GetMapping("/studentWithBook/{id}")
    public StudentBookResponce getStudentWithBook(@PathVariable int id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isEmpty()){
            throw new RuntimeException("student not found");
        }
        Student student = optionalStudent.get();
//        Calling book service using FeignClint
        Book book = bookClient.getBook(id);
        StudentBookResponce studentBookResponce = new StudentBookResponce();
        studentBookResponce.setStudentId(student.getStudentId());
        studentBookResponce.setName(student.getName());
        studentBookResponce.setEmail(student.getEmail());
        studentBookResponce.setBook(book);
        return studentBookResponce;
    }

    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student student){
        return studentRepository.save(student);
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id){
        Optional<Student> students = studentRepository.findById(id);
        if(students.isPresent()){
            return ResponseEntity.ok(students.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }


}
