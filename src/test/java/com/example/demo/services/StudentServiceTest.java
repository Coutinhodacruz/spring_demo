package com.example.demo.services;

import com.example.demo.exception.EmailNotFoundException;
import com.example.demo.exception.InvalidStudentIdException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Calendar.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudent() {

        List<Student> students = new ArrayList<>();
        students.add(new Student("bobby", "bobby@gmail.com", LocalDate.of(2000, MARCH, 22)));
        students.add(new Student("milner", "milner@gmail.com", LocalDate.of(1990, APRIL, 13)));
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getStudent();


        assertEquals(2, result.size());
        assertEquals("bobby", result.get(0).getName());
        assertEquals("bobby@example.com", result.get(1).getEmail());
    }

    @Test
    void testAddNewStudent() throws EmailNotFoundException {
        Student newStudent = new Student("charlie", "charlie@gmail.com", LocalDate.of(1990, APRIL, 13));
        when(studentRepository.findStudentByEmail(newStudent.getEmail())).thenReturn(Optional.empty());


        assertDoesNotThrow(() -> studentService.addNewStudent(newStudent));

        verify(studentRepository, times(1)).save(newStudent);
    }

    @Test
    void testAddNewStudentThrowsEmailNotFoundException() {

        Student existingStudent = new Student("Alice", "Alice@gmai.com", LocalDate.of(1980, APRIL, 13));
        when(studentRepository.findStudentByEmail(existingStudent.getEmail())).thenReturn(Optional.of(existingStudent));

        assertThrows(EmailNotFoundException.class, () -> studentService.addNewStudent(existingStudent));
    }

    @Test
    void testDeleteStudent() {
        Long studentIdToDelete = 1L;
        when(studentRepository.existsById(studentIdToDelete)).thenReturn(true);


        assertDoesNotThrow(() -> studentService.deleteStudent(studentIdToDelete));


        verify(studentRepository, times(1)).deleteById(studentIdToDelete);
    }

    @Test
    void testDeleteStudentThrowsInvalidStudentIdException() {

        Long nonExistingStudentId = 10L;
        when(studentRepository.existsById(nonExistingStudentId)).thenReturn(false);


        assertThrows(InvalidStudentIdException.class, () -> studentService.deleteStudent(nonExistingStudentId));
    }

    @Test
    void testUpdateStudent()  {

        String updateStudent = "Coco";
        Long studentIdToUpdate = 1L;
        Student existingStudent = new Student(updateStudent, "Alice", LocalDate.of(1990, OCTOBER, 13));
        when(studentRepository.findById(studentIdToUpdate)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.findStudentByEmail("newemail@example.com")).thenReturn(Optional.empty());


        assertDoesNotThrow(() -> studentService.updateStudent(studentIdToUpdate, "New Name", "newemail@example.com"));


        verify(studentRepository, times(1)).save(existingStudent);
        assertEquals("New Name", existingStudent.getName());
        assertEquals("newemail@example.com", existingStudent.getEmail());
    }

    @Test
    void testUpdateStudentThrowsInvalidStudentIdException() {

        Long nonExistingStudentId = 10L;
        when(studentRepository.findById(nonExistingStudentId)).thenReturn(Optional.empty());

        assertThrows(InvalidStudentIdException.class,
                () -> studentService.updateStudent(nonExistingStudentId, "New Name", "newemail@example.com"));
    }

//    @Test
//    void testUpdateStudentThrowsEmailNotFoundException() {
//        // Prepare
//        String  studentIdToUpdate = "Alice";
//        Student existingStudent = new Student(studentIdToUpdate, "Alice", "alice@example.com");
//        when(studentRepository.findById(studentIdToUpdate)).thenReturn(Optional.of(existingStudent));
//        when(studentRepository.findStudentByEmail("existing@example.com")).thenReturn(Optional.of(existingStudent));
//
//        // Execute and Verify
//        assertThrows(EmailNotFoundException.class,
//                () -> studentService.updateStudent(studentIdToUpdate, "New Name", "existing@example.com"));
//    }
}
