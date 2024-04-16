package com.example.dto;


import com.example.grpc.CreateStudentRequest;
import com.example.grpc.DeleteStudentRequest;
import com.example.grpc.UpdateStudentRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Introspected
@SerdeImport
public class StudentDto {
    private Integer id;

    private String email;

    private String name;

    private String address;

    private Integer age;

    private String gender;

    private List<String> courseIds;


    public static StudentDto toModel(CreateStudentRequest model){
        return StudentDto.builder()
                .id(model.getId())
                .email(model.getEmail())
                .name(model.getName())
                .address(model.getAddress())
                .age(model.getAge())
                .gender(model.getGender())
                .build();
    }

    public static StudentDto toModelUpdate(UpdateStudentRequest model){
        return StudentDto.builder()
                .id(model.getId())
                .email(model.getEmail())
                .name(model.getName())
                .address(model.getAddress())
                .age(model.getAge())
                .gender(model.getGender())
                .build();
    }


    public static com.example.grpc.StudentDto toGrpc(StudentDto response) {//new instance banayara pathauna vo
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
