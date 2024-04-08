package com.example.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.dto.StudentDto;
import com.example.model.Student;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class StudentServiceTest {

    @Inject
    StudentService studentService;

    @Inject
    CqlSession cqlSession;

    @Test
    void findStudentById() {
        //given
         Integer id=11;
         String name="bishal thakur"; //email
         String address="ithari";
         Integer age=22;
         String gender="male";

        //when
        Optional<StudentDto> result=studentService.findStudentById(id,name,age);

        //then
        assertTrue(result.isPresent());
        StudentDto student=result.get();
        assertEquals(id,student.getId());
        assertEquals(name,student.getName());
        assertEquals(address,student.getAddress());
        assertEquals(age,student.getAge());
        assertEquals(gender,student.getGender());

    }

    @Test
    void findAll(){

        List<StudentDto> students = studentService.findAllUsers();

        assertFalse(students.isEmpty());

    }

    @Test
    void testInsertStudent() {
//creating student data
        StudentDto student = StudentDto.builder()
                .id(5)
               .name("puja")
                .address("janakpur")
                .age(20)
                .gender("female")
                .build();

        studentService.insertStudent(student);


        //getting data from database
        var studentFromDb  = studentService.findStudentById(student.getId(),student.getName(),student.getAge());

        assertNotNull(studentFromDb.get());
        assertEquals(5, studentFromDb.get().getId());
       assertEquals("puja", studentFromDb.get().getName());
        assertEquals("janakpur",studentFromDb.get().getAddress());
       assertEquals(20, studentFromDb.get().getAge());
        assertEquals("female",studentFromDb.get().getGender());
    }

    @Test
    void testDeleteStudentById() {

        Integer id = 10;
        String name = "deepa";
        Integer age = 30;

        Optional<StudentDto> result=studentService.findStudentById(id,name,age);
        boolean deleted = studentService.deleteStudentById(id, name, age);


        assertTrue(deleted);
        assertFalse(result.isPresent());
    }
//
//
//
//
//
}