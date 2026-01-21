package com.student.api.StudentApi.controller;

import com.student.api.StudentApi.entities.Student;
import com.student.api.StudentApi.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService ;

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents(){

      return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student){

        return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student,@PathVariable Long id){

        return new ResponseEntity<>(studentService.updateStudent(student,id), HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){

        return new ResponseEntity<String>(studentService.deleteStudent(id), HttpStatus.FOUND);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){

        return new ResponseEntity<>(studentService.getStudent(id), HttpStatus.FOUND);
    }

}
