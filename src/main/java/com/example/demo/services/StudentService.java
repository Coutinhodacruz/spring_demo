package com.example.demo.services;

import com.example.demo.model.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {

    public List<Student> getStudent(){
        return List.of(
                new Student(
                        1L,
                        "Coutinho",
                        "coutinho@gmail.com",
                        LocalDate.of(2000, Month.MARCH, 22),
                        21
                )
        );
    }
}
