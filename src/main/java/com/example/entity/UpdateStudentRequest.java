package com.example.entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Introspected
@SerdeImport
public class UpdateStudentRequest {

    private String address;
    private String gender;
    private String name;
    private String age;
    private List<Integer> courseIds;
}
