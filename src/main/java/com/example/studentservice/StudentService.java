package com.example.studentservice;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.dto.StudentDto;
import com.example.exception.NotFoundException;
import com.example.exception.StudentNotFoundException;
import com.example.grpc.CreateStudentRequest;

import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class StudentService {

    private final CqlSession cqlSession;

    public StudentService(CqlSessionBuilder cqlSessionBuilder) {
        this.cqlSession = cqlSessionBuilder.build();
        this.findByIdStatement = cqlSession.prepare(FIND_QUERY);
        this.updateStatement=cqlSession.prepare(UPDATE_QUERY);
        this.deleteStatement=cqlSession.prepare(DELETE_QUERY);
        this.findAllStatement=cqlSession.prepare(FINDALL_QUERY);
        INSERT_STATEMENT = cqlSession.prepare(INSERT_QUERY);
    }
    public final static String INSERT_QUERY ="INSERT INTO mykeyspace.Student (id, email,name,address,age,gender) VALUES (?,?, ?,?,?,?)";
    public final static String FIND_QUERY ="SELECT * FROM mykeyspace.Student WHERE id = ? and email = ?";

    public final static String UPDATE_QUERY="UPDATE mykeyspace.Student SET name = ? ,address = ?,age = ?, gender = ? WHERE id = ? and email = ?";

    public final static String DELETE_QUERY="DELETE FROM mykeyspace.Student WHERE id = ? and email = ?";

    public final static String FINDALL_QUERY="SELECT * FROM mykeyspace.Student";
    public static PreparedStatement INSERT_STATEMENT;

    private final PreparedStatement findByIdStatement;

    private final PreparedStatement updateStatement;

    private final PreparedStatement deleteStatement;

   private final PreparedStatement findAllStatement;

    public StudentDto insertStudent(StudentDto student){
        var boundStatement = INSERT_STATEMENT.bind
                (
                        student.getId(),
                        student.getEmail(),
                        student.getName(),
                        student.getAddress(),
                        student.getAge(),
                        student.getGender()
                );
         cqlSession.execute(boundStatement);
     return findStudentById(student.getId(),student.getEmail()).orElseThrow(NotFoundException::new);
    }

    public List<StudentDto> findAllUsers() {
        List<StudentDto> studentList = new ArrayList<>();
        ResultSet rs = cqlSession.execute(findAllStatement.bind());
        if(rs.isFullyFetched()){
            rs.forEach(row -> {
                StudentDto student = new StudentDto();
                student.setId(row.getInt("id"));
                student.setEmail(row.getString("email"));
                student.setName(row.getString("name"));
                student.setAddress(row.getString("address"));
                student.setAge(row.getInt("age"));
                student.setGender(row.getString("gender"));
                studentList.add(student);
            });
            return studentList;
        }else {
             throw new NotFoundException("No student present");
        }

    }

    public Optional<StudentDto> findStudentById(Integer id,String email) {
        ResultSet rs = cqlSession.execute(findByIdStatement.bind(id,email));
        Row row = rs.one();
        if (row != null) {
            StudentDto student=new StudentDto();
            student.setId(row.getInt("id"));
            student.setEmail(row.getString("email"));
            student.setName(row.getString("name"));
            student.setAddress(row.getString("address"));
            student.setAge(row.getInt("age"));
            student.setGender(row.getString("gender"));
            return Optional.of(student);
        }

        return Optional.empty();//optional xa vana throw nagarda ni hunxa
    }

    public StudentDto updateStudent(String newName,
                                    String newAddress,
                                    Integer newAge ,
                                    String newGender ,
                                    Integer id,
                                    String email ) {

        Optional<StudentDto> studentDto=findStudentById(id,email);
        if(studentDto.isEmpty()){
            throw new NotFoundException("Cannot find student by given id and email");
        }

         cqlSession.execute(updateStatement.bind( newName, newAddress, newAge,newGender,id,email));

        Optional<StudentDto> updatedStudentDto=findStudentById(id,email);
        if (updatedStudentDto.isPresent()) {
            return studentDto.get();
        }else{
            throw new NotFoundException("Cannot find student by given id and email");
        }
    }



    public boolean deleteStudentById(Integer id ,String email) {
        var studentDto=findStudentById(id,email);
        if (studentDto.isEmpty()) throw new NotFoundException("Cannot find student by given id and email");

        ResultSet rs = cqlSession.execute(deleteStatement.bind(id,email));
        return rs.wasApplied();
    }
}
