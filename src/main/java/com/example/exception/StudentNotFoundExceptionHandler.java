package com.example.exception;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public class StudentNotFoundExceptionHandler implements ExceptionHandler<StudentNotFoundException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, StudentNotFoundException exception) {
        return HttpResponse.notFound("studnet not found with id:");
    }
}
