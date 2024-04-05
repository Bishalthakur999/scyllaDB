package com.example.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Introspected
@SerdeImport
public class UpdateStudentRequest {

    private String address;

    private String gender;


}
