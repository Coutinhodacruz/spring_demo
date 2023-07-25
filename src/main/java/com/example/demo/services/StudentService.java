package com.example.demo.services;

import com.example.demo.exception.EmailNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudent(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) throws EmailNotFoundException {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());

        if (studentByEmail.isPresent()){
            throw new EmailNotFoundException("email taken");
        }
        studentRepository.save(student);
    }
}
