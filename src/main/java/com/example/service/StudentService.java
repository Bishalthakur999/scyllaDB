package com.example.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.dto.StudentDto;
import com.example.exception.StudentNotFoundException;
import com.example.model.Student;
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
    public final static String INSERT_QUERY ="INSERT INTO mykeyspace.Student (id, name,address,age,gender) VALUES (?, ?,?,?,?)";
    public final static String FIND_QUERY ="SELECT * FROM mykeyspace.Student WHERE id = ? and name = ? and age= ?";

    public final static String UPDATE_QUERY="UPDATE mykeyspace.Student SET address = ?, gender = ? WHERE id = ? and name = ? and age = ?";

    public final static String DELETE_QUERY="DELETE FROM mykeyspace.Student WHERE id = ? and name=? and age=?";

    public final static String FINDALL_QUERY="SELECT * FROM mykeyspace.Student";
    public static PreparedStatement INSERT_STATEMENT;

    private final PreparedStatement findByIdStatement;

    private final PreparedStatement updateStatement;

    private final PreparedStatement deleteStatement;

   private final PreparedStatement findAllStatement;

    public void insertStudent(StudentDto student){
        var boundStatement = INSERT_STATEMENT.bind(student.getId(),student.getName(),student.getAddress(),student.getAge(),student.getGender());
         cqlSession.execute(boundStatement);
    }

    public List<StudentDto> findAllUsers() {
        List<StudentDto> stu = new ArrayList<>();
        ResultSet rs = cqlSession.execute(findAllStatement.bind());
        rs.forEach(row -> {
            StudentDto student = new StudentDto();
            student.setId(row.getInt("id"));
            student.setName(row.getString("name"));
            student.setAddress(row.getString("address"));
            student.setAge(row.getInt("age"));
            student.setGender(row.getString("gender"));
            stu.add(student);
        });
        return stu;
    }

    public Optional<StudentDto> findStudentById(Integer id,String name,Integer age) {
        ResultSet rs = cqlSession.execute(findByIdStatement.bind(id,name,age));
        Row row = rs.one();
        if (row != null) {
            StudentDto student=new StudentDto();
            student.setId(row.getInt("id"));
            student.setName(row.getString("name"));
            student.setAddress(row.getString("address"));
            student.setAge(row.getInt("age"));
            student.setGender(row.getString("gender"));
            return Optional.of(student);
        }
        throw new StudentNotFoundException("student not found with id:"+id);
    }

    public boolean updateStudent(Integer id, String name, String newAddress, Integer age, String newGender) {
        ResultSet rs = cqlSession.execute(updateStatement.bind(newAddress, newGender, id, name, age));
        return rs.wasApplied();
    }



    public boolean deleteStudentById(Integer id ,String name,Integer age) {
        ResultSet rs = cqlSession.execute(deleteStatement.bind(id,name,age));
        return rs.wasApplied();
    }
}
