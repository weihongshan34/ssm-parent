package com.qf.impl;

import com.qf.CourseService;
import com.qf.dao.CourseMapper;
import com.qf.dao.SpeakerMapper;
import com.qf.dao.VideoMapper;
import com.qf.pojo.Course;
import com.qf.pojo.Speaker;
import com.qf.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private SpeakerMapper speakerMapper;

    public List<Course> findAll() {
        List<Course> courseList = courseMapper.selectByExample(null);
        List<Video> videos = null;
        Speaker speaker=null;
        for (int i = 0; i < courseList.size(); i++) {
            videos = videoMapper.selectByCid(courseList.get(i).getId());
            for (int j = 0; j < videos.size(); j++) {
                speaker = speakerMapper.selectByPrimaryKey(videos.get(j).getSpeakerId());
                videos.get(j).setSpeaker(speaker);
            }
            courseList.get(i).setVideoList(videos);
        }
        return courseList;
    }

    public Course findByid(int id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        List<Video> videos = videoMapper.selectByCid(id);
        Speaker speaker;
        for (int i = 0; i < videos.size(); i++) {
             speaker = speakerMapper.selectByPrimaryKey(videos.get(i).getSpeakerId());
             videos.get(i).setSpeaker(speaker);
        }
        course.setVideoList(videos);
        return course;
    }

    public List<Course> findBySid(int sid) {
        List<Course> courses = courseMapper.selectBySid(sid);
        List<Video> videos = null;
        Speaker speaker = null;
        for (int i = 0; i < courses.size(); i++) {
           videos = videoMapper.selectByCid(courses.get(i).getId());
            for (int j = 0; j < videos.size(); j++) {
                speaker = speakerMapper.selectByPrimaryKey(videos.get(j).getSpeakerId());
                videos.get(j).setSpeaker(speaker);
            }
            courses.get(i).setVideoList(videos);
        }
        return courses;
    }
}
