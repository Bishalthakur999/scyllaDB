package com.example.studentservice;

import com.example.entity.Course;

import java.util.List;

public interface CourseDao {
    public Course createCourse(Course course );
    public List<Course> findAllCourse();
    public Course findCouresById(Integer cId,String cName);

    public boolean deleteCourseById(Integer cId, String cName);
    boolean courseExists(int courseId);
}
