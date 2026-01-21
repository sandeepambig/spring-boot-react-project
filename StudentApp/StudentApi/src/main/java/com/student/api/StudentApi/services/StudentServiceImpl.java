package com.student.api.StudentApi.services;

import com.student.api.StudentApi.entities.Student;
import com.student.api.StudentApi.exceptions.StudentNotFoundException;
import com.student.api.StudentApi.repositories.StudentRepository;

import java.util.List;
import com.student.api.StudentApi.exceptions.StudentAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements  StudentService{

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {

        if(studentAlreadyExists(student.getEmail())){
            throw new StudentAlreadyExistsException(student.getEmail()+ "already exist");
        }
        Student savedStudent = studentRepository.save(student);
        return savedStudent;
    }

    private boolean studentAlreadyExists(String email) {

        return studentRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(Long id) {

        Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException("Student not found"));
        return student;
    }

    @Override
    public Student updateStudent(Student student, Long id) {

       Student student1 = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException("Student not found"));
       student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setEmail(student.getEmail());
        student1.setDepartment(student.getDepartment());

        return studentRepository.save(student1);
    }

    @Override
    public String deleteStudent(Long id) {

        Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException("Student not found"));
        studentRepository.delete(student);
        return "Successfully student deleted";
    }
}
