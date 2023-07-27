package com.example.demo.services;

import com.example.demo.exception.EmailNotFoundException;
import com.example.demo.exception.InvalidStudentIdException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
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

    public void deleteStudent(Long studentId) throws InvalidStudentIdException {
      boolean exists = studentRepository.existsById(studentId);
      if (!exists){
          throw new InvalidStudentIdException("Student with id " + studentId + "does not exists");
      }
      studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId,
                              String name,
                              String email) throws InvalidStudentIdException, EmailNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new InvalidStudentIdException("Student with id " + studentId + "does not exists"));

        if (name != null &&
                    name.length() > 0 &&
                    !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if (email != null &&
                    email.length() > 0 &&
                    !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentByEmail = studentRepository
                    .findStudentByEmail(email);
            if (studentByEmail.isPresent()){
                throw new EmailNotFoundException("email taken");
            }
            student.setEmail(email);
        }

    }
}
