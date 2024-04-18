//package com.example.service;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import com.datastax.oss.driver.api.core.cql.ResultSet;
//import com.datastax.oss.driver.api.core.cql.Row;
//import com.example.dto.StudentDto;
//import com.example.exception.StudentNotFoundException;
//import com.example.model.Student;
//import com.example.studentservice.StudentService;
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.function.Executable;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@MicronautTest
//class StudentServiceTest {
//
//    @Inject
//    StudentService studentService;
//
//    @Inject
//    CqlSession cqlSession;
//
//    @Test
//    void findStudentById() {
//        //given
//         Integer id=5;
//         String name="puja"; //email
//         String address="janakpur";
//         Integer age=20;
//         String gender="female";
//
//        //when
//
//
//        Optional<StudentDto> result=studentService.findStudentById(5,"puja",20);
//        if(result.isPresent()){
//            //then
//            assertTrue(result.isPresent());
//            StudentDto student=result.get();
//            assertEquals(id,student.getId());
//            assertEquals(name,student.getName());
//            assertEquals(address,student.getAddress());
//            assertEquals(age,student.getAge());
//            assertEquals(gender,student.getGender());
//
//        }
//
//    }
//
//    @Test
//    void test_NotFoundExcetpionWhile_FindingStudentFromId(){
//        Integer id=2;
//        String name="puja"; //email
//        String address="janakpur";
//        Integer age=20;
//        String gender="female";
//
//        assertThrows(StudentNotFoundException.class, () -> studentService.findStudentById(id,name,age));
//    }
//
//    @Test
//    void findAll(){
//
//        List<StudentDto> students = studentService.findAllUsers();
//
//        assertFalse(students.isEmpty());
//
//    }
//
//    @Test
//    void testInsertStudent() {
////creating student data
//        StudentDto student = StudentDto.builder()
//                .id(5)
//               .name("puja")
//                .address("janakpur")
//                .age(20)
//                .gender("female")
//                .build();
//
//        studentService.insertStudent(student);
//
//
//        //getting data from database
//        var studentFromDb  = studentService.findStudentById(student.getId(),student.getName(),student.getAge());
//
//        assertNotNull(studentFromDb.get());
//        assertEquals(5, studentFromDb.get().getId());
//       assertEquals("puja", studentFromDb.get().getName());
//        assertEquals("janakpur",studentFromDb.get().getAddress());
//       assertEquals(20, studentFromDb.get().getAge());
//        assertEquals("female",studentFromDb.get().getGender());
//    }
//
//    @Test
//    void testDeleteStudentById() {
//
//        Integer id = 6;
//        String name = "puja";
//        Integer age = 20;
//       // insert
//        //test
//        //delete
//        //get
//        //test
//        Optional<StudentDto> beforeDeletion=studentService.findStudentById(id,name,age);
//        StudentDto deleted = studentService.deleteStudentById(id, name, age);
//        Optional<StudentDto> afterDeletion=studentService.findStudentById(id,name,age);
//
//
//
//            assertFalse(afterDeletion.isPresent());
//
//    }
//
//
//
//    @Test
//    void updateStudent(){
//        // Given
//        Integer id = 5;
//        String name = "puja";
//        String newAddress = "abc";//orignal lekhan ho ya mula
//        Integer age = 20;
//        String newGender = "female";
//
//
//        Optional<StudentDto> result=studentService.findStudentById(id,name,age);
//
//        if(result.isPresent()){
//            StudentDto updatedStudentDto = studentService.updateStudent(5, "puja","damak", 20, "female");
//
//            assertEquals(id, updatedStudentDto.getId());
//            assertEquals(name, updatedStudentDto.getName());
//            assertEquals(newAddress, updatedStudentDto.getAddress());
//            assertEquals(age, updatedStudentDto.getAge());
//            assertEquals(newGender, updatedStudentDto.getGender());
//        }
//
//
//
//    }
//
//    @Test
//    void testUpdateStudent_StudentNotFound(){
//
//        Integer id=2;
//        String name="puja";
//        String address="janakpur";
//        Integer age=20;
//        String gender="female";
//
//        assertThrows(StudentNotFoundException.class, () -> studentService.findStudentById(id,name,age));
//
//    }
//
//
//
//
//
//}