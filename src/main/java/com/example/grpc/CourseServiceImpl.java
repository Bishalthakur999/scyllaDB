package com.example.grpc;

import com.example.entity.Course;
import com.example.studentservice.CourseDao;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;

@Singleton
public class CourseServiceImpl extends CourseServiceGrpc.CourseServiceImplBase{

    private final CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public void getAllCourse(EmptyList request, StreamObserver<CreateCourseRequest> responseObserver) {


        //super.getAllCourse(request, responseObserver);
    }

    @Override
    public void findCourseById(FindCourseRequest request, StreamObserver<CreateCourseRequest> responseObserver) {


       // super.findCourseById(request, responseObserver);
    }

    @Override
    public void createCourse(CreateCourseRequest request, StreamObserver<CreateCourseRequest> responseObserver) {
        var courseModel = Course.toModel(request);

        var createdCourse= courseDao.createCourse(courseModel);
        responseObserver.onNext(Course.toRpc(createdCourse));
        responseObserver.onCompleted();

        //super.createCourse(request, responseObserver);
    }

    @Override
    public void deleteStudentById(FindCourseRequest request, StreamObserver<EmptyList> responseObserver) {

        try{
            boolean deletedStudent=courseDao.deleteCourseById(request.getCId(),request.getCName());

            if (deletedStudent) {
                // Send an empty response to indicate success
                responseObserver.onNext(EmptyList.getDefaultInstance());
                responseObserver.onCompleted();
            }

        }catch (Exception e){
            e.printStackTrace();
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
            return;
        }

       // responseObserver.onNext(deletedStudent);
        responseObserver.onCompleted();
        //super.deleteStudentById(request, responseObserver);
    }


}
