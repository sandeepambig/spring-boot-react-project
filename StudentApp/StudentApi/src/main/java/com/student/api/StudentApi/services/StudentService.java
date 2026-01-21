package com.student.api.StudentApi.services;

import com.student.api.StudentApi.entities.Student;

import java.util.List;

public interface StudentService {

    Student addStudent(Student student);
    List<Student> getAllStudents();
    Student getStudent(Long id);
    Student updateStudent(Student student,Long id);
    String deleteStudent(Long id);
}
