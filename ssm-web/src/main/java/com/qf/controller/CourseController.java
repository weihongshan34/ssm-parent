package com.qf.controller;

import com.qf.CourseService;
import com.qf.dao.SubjectMapper;
import com.qf.pojo.Course;
import com.qf.pojo.Subject;
import com.qf.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private SubjectMapper subjectMapper;
    @RequestMapping("course/{subjectId}")
    public String findBySid(@PathVariable int subjectId, Model model){
        List<Course> courseList = courseService.findBySid(subjectId);
        model.addAttribute("courseList",courseList);
        Subject subject = subjectMapper.selectByPrimaryKey(subjectId);
        subject.setCourseList(courseList);
        model.addAttribute("subject",subject);
        List<Subject> subjectList = subjectMapper.selectByExample(null);
        model.addAttribute("subjectList",subjectList);
        return "/before/course.jsp";
    }

}
