package com.example.studentservice;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.dto.Course;
import com.example.exception.NotFoundException;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;


@Singleton
public class CourseDaoImpl implements CourseDao {

    private final CqlSession cqlSession;
    public CourseDaoImpl(CqlSession cqlSession) {
        this.cqlSession = cqlSession;
        insertStatement=cqlSession.prepare(INSERT_QUERY);
        findByIdStatement=cqlSession.prepare(FIND_QUERY);
        findAllStatement=cqlSession.prepare(FIND_ALL);
        deleteByIdStatement=cqlSession.prepare(DELETE_QUERY);
    }

    public static final String INSERT_QUERY="INSERT INTO mykeyspace.Course(cId,cName,cHrs) VALUES(?,?,?)";

    public static final String FIND_ALL="SELECT * FROM mykeyspace.Course";

    public static final String FIND_QUERY="SELECT * FROM mykeyspace.Course WHERE cId = ? and cName = ?";

    public static final String DELETE_QUERY="DELETE  FROM mykeyspace.Course WHERE cId = ? and cName = ? ";

    public static PreparedStatement insertStatement;
    public static PreparedStatement findAllStatement;
    public static PreparedStatement findByIdStatement;
    public static PreparedStatement deleteByIdStatement;



    @Override
    public Course createCourse(Course course) {
        var insertCourse=insertStatement.bind(
                course.getCId(),
                course.getCName(),
                course.getCHrs()
        );
        cqlSession.execute(insertCourse);
        return findCouresById(course.getCId(),course.getCName());
    }

    @Override
    public List<Course> findAllCourse() {
        List<Course> courseList =new ArrayList<>();
       var courses=  cqlSession.execute(findAllStatement.bind());
      courses.forEach(row -> {
          Course course=new Course();
          course.setCId(row.getInt("cId"));
          course.setCName(row.getString("cName"));
          course.setCHrs(row.getInt("cHrs"));
          courseList.add(course);
      });


        return courseList;
    }

    @Override
    public Course findCouresById(Integer cId ,String cName) {


          ResultSet course= cqlSession.execute(findByIdStatement.bind(cId,cName));
          Row row=course.one();

          if(row !=null){
              Course obtainedCourse=new Course();
              obtainedCourse.setCId(row.getInt("cId"));
              obtainedCourse.setCName(row.getString("cName"));
              obtainedCourse.setCHrs(row.getInt("cHrs"));

              return obtainedCourse;
          }

        return null;
    }

    @Override
    public boolean deleteCourseById(Integer cId, String cName) {

        Course course= findCouresById(cId,cName);
        if(course ==null){
            throw new NotFoundException("course not found");
        }
        else{
           ResultSet rs= cqlSession.execute(deleteByIdStatement.bind(cId,cName));
           return rs.wasApplied();
        }



    }
}
