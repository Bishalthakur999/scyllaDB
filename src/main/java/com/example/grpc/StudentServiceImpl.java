package com.example.grpc;

import com.example.dto.StudentDto;
import com.example.studentservice.StudentService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;


@Singleton
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {


    private final StudentService studentService;

    public StudentServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void getAllUsers(Empty request, StreamObserver<StudentsList> responseObserver) {

        try{
            //database bata user leko
            var userList = studentService.findAllUsers();//Sudent dto -> rpc dto

            //converting from service dto to grpc dto.
            var grpcUserList = userList.stream()
                    .map(StudentDto::toGrpc)
                    .toList();


            //converting from service dto to grpc dto.
            var rpcStudentList = StudentsList.newBuilder()
                    .addAllStudents(grpcUserList)
                    .build();
            responseObserver.onNext(rpcStudentList);

        }catch (Exception e){
            e.printStackTrace();
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
            return;

        }
        responseObserver.onCompleted();
    }

    @Override
    public void findByID(FindStudentRequest request, StreamObserver<com.example.grpc.StudentDto> responseObserver) {



       try {
           var user = studentService.findStudentById(request.getId(), request.getEmail());

//           if (user.isEmpty()) {
//               responseObserver.onCompleted();
//               return;
//           }

           var grpcStundet = StudentDto.toGrpc(user.get());

           responseObserver.onNext(grpcStundet);
       }catch (Exception e){
           e.printStackTrace();
           responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
           return;

       }
        responseObserver.onCompleted();
    }

    @Override
    public void save(CreateStudentRequest request, StreamObserver<com.example.grpc.StudentDto> responseObserver) {
        //database ma halako hai
       var addedStudent= studentService.insertStudent(StudentDto.toModel(request));
        responseObserver.onNext(StudentDto.toGrpc(addedStudent));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteStudent(DeleteStudentRequest request, StreamObserver<com.example.grpc.StudentDto> responseObserver) {

try {
    var student=studentService.deleteStudentById(request.getId(), request.getEmail());

}catch (Exception e) {
    e.printStackTrace();
    responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());
    return;
}
        //deleted request li rpc ma convert garako hai
      //  var grpcDeleted=StudentDto.toGrpc(student);
       // responseObserver.onNext(grpcDeleted);
        responseObserver.onCompleted();
    }

    @Override
    public void updateStudent(UpdateStudentRequest request, StreamObserver<com.example.grpc.StudentDto> responseObserver) {

        try{
            var studentDto = StudentDto.toModelUpdate(request);
            StudentDto updatedStudent= studentService.updateStudent(
                    studentDto.getName(),
                    studentDto.getAddress(),
                    studentDto.getAge(),
                    studentDto.getGender(),
                    studentDto.getId(),
                    studentDto.getEmail()

            );
            // updated student lai rpc ma convert garako hai mula
            com.example.grpc.StudentDto grpcUpdatedStudent = StudentDto.toGrpc(updatedStudent);
            responseObserver.onNext(grpcUpdatedStudent);


        }catch (Exception e){
            e.printStackTrace();
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());

        }



        responseObserver.onCompleted();

    }







}
