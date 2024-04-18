package com.example.entity;



import com.example.grpc.Course;
import com.example.grpc.CreateStudentRequest;
import com.example.grpc.UpdateStudentRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Introspected
@SerdeImport
public class Student {
    private Integer id;

    private String email;

    private String name;

    private String address;

    private Integer age;

    private String gender;

    private List<Integer> courseIds;


    public static Student toModel(CreateStudentRequest model){
        return Student.builder()
                .id(model.getId())
                .email(model.getEmail())
                .name(model.getName())
                .address(model.getAddress())
                .age(model.getAge())
                .gender(model.getGender())
                .build();
    }

    public static Student toModelUpdate(UpdateStudentRequest model){
        return Student.builder()
                .id(model.getId())
                .email(model.getEmail())
                .name(model.getName())
                .address(model.getAddress())
                .age(model.getAge())
                .gender(model.getGender())

              .courseIds(model.getCoursesList().stream().map(Course::getCourseId).collect(Collectors.toList()))
                .build();
    }


    public static com.example.grpc.StudentDto toGrpc(Student response) {//new instance banayara pathauna vo
        return  com.example.grpc.StudentDto.newBuilder()
                .setId(response.getId())
                .setEmail(response.getEmail())
                .setName(response.getName())
                .setAddress(response.getAddress())
                .setAge(response.getAge())
                .setGender(response.getGender())
                .build();
    }






}
