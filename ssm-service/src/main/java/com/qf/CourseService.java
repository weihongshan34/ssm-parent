package com.qf;

import com.qf.pojo.Course;

import java.util.List;

public interface CourseService {
    public List<Course> findAll();
    public Course findByid(int id);
    public List<Course> findBySid(int sid);

}
