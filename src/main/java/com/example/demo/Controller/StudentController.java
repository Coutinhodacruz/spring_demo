package com.example.demo.Controller;

import com.example.demo.exception.EmailNotFoundException;
import com.example.demo.exception.InvalidStudentIdException;
import com.example.demo.model.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudent(){
        return studentService.getStudent();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) throws EmailNotFoundException {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) throws InvalidStudentIdException {
        studentService.deleteStudent(studentId);

    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) throws InvalidStudentIdException {
        try {
            studentService.updateStudent(studentId, name, email);
        } catch (EmailNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
