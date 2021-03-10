package com.qf.impl;

import com.qf.SubjectService;
import com.qf.dao.CourseMapper;
import com.qf.dao.SubjectMapper;
import com.qf.pojo.Course;
import com.qf.pojo.CourseExample;
import com.qf.pojo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private CourseMapper courseMapper;
    public List<Subject> getAll() {

        List<Subject> subjects = subjectMapper.selectByExample(null);
        List<Course> courses = null;
        for (int i = 0; i < subjects.size(); i++) {
           courses = courseMapper.selectBySid(subjects.get(i).getId());
            subjects.get(i).setCourseList(courses);
        }
        return subjects;
    }
}
