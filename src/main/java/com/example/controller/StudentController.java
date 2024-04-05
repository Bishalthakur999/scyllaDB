package com.example.controller;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.example.model.Student;
import com.example.model.UpdateStudentRequest;
import com.example.service.StudentService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;



@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }


    @Get("/find")
    public List<Student> getAllUsers() {
        return studentService.findAllUsers();
    }

    @Post("/save")
    public Student saveStudent(@Body Student student) {
        studentService.insertStudent(student);
        return student;
    }

    @Get("find/{id}/{name}/{age}")
    public Optional<Student> getUserById( Integer id ,String name, Integer age) {
        return studentService.findStudentById(id,name,age);
    }

//    @Put("update/{id}/{name}/{age}")
//    public boolean updateUser(Integer id , String name, String address, Integer age, String gender) {
//        return studentService.updateStudent(id,name,address,age,gender);
//    }


    @Put("update/{id}/{name}/{age}")
    public HttpResponse<?> updateStudent(
            @PathVariable Integer id,
            @PathVariable String name,
            @PathVariable Integer age,
            @Body UpdateStudentRequest updateStudentRequest) {
        boolean updated = studentService.updateStudent(id, name, updateStudentRequest.getAddress(), age, updateStudentRequest.getGender());
        if (updated) {
            return HttpResponse.ok("updated suscussfully");

        } else {
            return HttpResponse.notFound();
        }
    }

    @Delete("delete/{id}/{name}/{age}")
    public boolean deleteUserById(Integer id,String name,Integer age) {
        return studentService.deleteStudentById(id,name,age);
    }
}
