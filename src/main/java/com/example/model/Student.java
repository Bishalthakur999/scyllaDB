package com.example.model;


import com.example.dto.StudentDto;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Introspected
@SerdeImport
public class Student {

    private Integer id;

    private String name;

    private String address;

    private Integer age;

    private String gender;



}
