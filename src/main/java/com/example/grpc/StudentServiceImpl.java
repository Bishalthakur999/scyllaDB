package com.example.grpc;

import com.example.entity.Student;
import com.example.exception.NotFoundException;
import com.example.studentservice.CourseDao;
import com.example.studentservice.StudentService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;

import java.util.List;


@Singleton
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {


    private final StudentService studentService;

    private final CourseDao courseDao;

    public StudentServiceImpl(StudentService studentService, CourseDao courseDao) {
        this.studentService = studentService;
        this.courseDao = courseDao;
    }

    @Override
    public void getAllUsers(Empty request, StreamObserver<StudentsList> responseObserver) {

        try{
            //database bata user leko
            var userList = studentService.findAllUsers();//Sudent dto -> rpc dto

            //converting from service dto to grpc dto.
            var grpcUserList = userList.stream()
                    .map(Student::toGrpc)
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

           var grpcStundet = Student.toGrpc(user.get());

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
       var addedStudent= studentService.insertStudent(Student.toModel(request));
        responseObserver.onNext(Student.toGrpc(addedStudent));
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
    public void updateStudent(UpdateStudentRequest request, StreamObserver<StudentDto> responseObserver) {
        try{
            var studentDto = Student.toModelUpdate(request);

            //cheak garna if provided courseId exist in course table
            checkIfCourseExists(request.getCoursesList());
            Student updatedStudent= studentService.updateStudent(
                    studentDto.getName(),
                    studentDto.getAddress(),
                    studentDto.getAge(),
                    studentDto.getGender(),
                    studentDto.getCourseIds(),
                    studentDto.getId(),
                    studentDto.getEmail()

            );
            // Student updatedStudent=studentService.updateStudent();
            // updated student lai rpc ma convert garako hai mula
            com.example.grpc.StudentDto grpcUpdatedStudent = Student.toGrpc(updatedStudent);
            responseObserver.onNext(grpcUpdatedStudent);


        }catch (Exception e){
            e.printStackTrace();
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asException());

        }



        responseObserver.onCompleted();

    }

    private void checkIfCourseExists(List<Course> courseIds) {
        for (Course courses: courseIds){
            boolean courseExists= courseDao.courseExists(courses.getCourseId());
            if (!courseExists){
               throw new NotFoundException("course with id: "+courses.getCourseId()+" doesnot exists.");
            }
        }

    }


}
