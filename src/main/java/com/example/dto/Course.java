package com.example.dto;

import com.example.grpc.CreateCourseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Introspected
@SerdeImport
public class Course {

    private Integer cId;

    private String cName;

    private Integer cHrs;

    public static Course toModel(CreateCourseRequest model){
        return Course.builder()
                .cId(model.getCId())
                .cName(model.getCName())
                .cHrs(model.getCHrs())
                .build();
    }

    public static com.example.grpc.CreateCourseRequest toRpc(Course response){
        return com.example.grpc.CreateCourseRequest.newBuilder()
                .setCId(response.getCId())
                .setCName(response.getCName())
                .setCHrs(response.getCHrs())
                .build();
    }

//    public static com.example.grpc.Course toRpc(Course respones){
//        return com.example.grpc.Course
//
//    }



}
