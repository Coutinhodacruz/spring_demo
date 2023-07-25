package com.example.demo.config;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.util.Calendar.MARCH;
import static java.util.Calendar.MAY;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
           Student coutinho = new Student(
                    "Coutinho",
                    "coutinho@gmail.com",
                    LocalDate.of(2000, MARCH, 22)

            );

            Student dacruz = new Student(
                    "dacruz",
                    "dacruz@gmail.com",
                    LocalDate.of(2002, MAY, 3)

            );

            repository.saveAll(
                    List.of(coutinho, dacruz)
            );

        };
    }
}
