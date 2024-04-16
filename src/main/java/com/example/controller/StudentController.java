//package com.example.controller;
//
//import com.example.dto.StudentDto;
//import com.example.dto.UpdateStudentRequestDto;
//import com.example.model.Student;
//import com.example.model.UpdateStudentRequest;
//import com.example.service.StudentService;
//import io.micronaut.http.HttpResponse;
//import io.micronaut.http.annotation.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//import java.util.Optional;
//
//
//
//@Controller
//@RequiredArgsConstructor
//@Slf4j
//public class StudentController {
//
//    private final StudentService studentService;
//
////    public StudentController(StudentService studentService) {
////        this.studentService = studentService;
////    }
//
//
//    @Get("/find")
//    public List<StudentDto> getAllUsers() {
//        log.info("Inside getAllUsers");
//        return studentService.findAllUsers();
//    }
//
//    @Post("/save")
//    public StudentDto saveStudent(@Body StudentDto student) {
//        studentService.insertStudent(student);
//        return student;
//    }
//
//    @Get("find/{id}/{name}/{age}")
//    public Optional<StudentDto> getUserById( Integer id ,String name, Integer age) {
//        return studentService.findStudentById(id,name,age);
//    }
//
////    @Put("update/{id}/{name}/{age}")
////    public boolean updateUser(Integer id , String name, String address, Integer age, String gender) {
////        return studentService.updateStudent(id,name,address,age,gender);
////    }
//
//
//    @Put("update/{id}/{name}/{age}")
//    public StudentDto updateStudent(@PathVariable Integer id,
//                                         @PathVariable String name,
//                                         @PathVariable Integer age,
//                                         @Body UpdateStudentRequestDto updateStudentRequestDto) {
//
//        StudentDto updated = studentService.updateStudent(id, name, updateStudentRequestDto.getAddress(),
//                age, updateStudentRequestDto.getGender());
//
//        return updated;
//
//
//
//    }
//
//    @Delete("delete/{id}/{name}/{age}")
//    public StudentDto deleteUserById(Integer id,String name,Integer age) {
//        return studentService.deleteStudentById(id,name,age);
//    }
//}
