package com.qf.controller;

import com.github.pagehelper.PageInfo;
import com.qf.CourseService;
import com.qf.SpeakerService;
import com.qf.SubjectService;
import com.qf.VideoService;
import com.qf.pojo.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SpeakerService speakerService;
    @Autowired
    private SubjectService subjectService;

    @RequestMapping("list")
    public String findAllBypage(QueryVo queryVo,Model model){
        model.addAttribute("query",queryVo);
        PageInfo allByPage = videoService.findAllByPage(queryVo);
        model.addAttribute("page",allByPage);
        List<Video> list = allByPage.getList();
        model.addAttribute("videos",list);

        model.addAttribute("page", allByPage);

        List<Course> allCourse = courseService.findAll();
        model.addAttribute("courseList", allCourse);
        List<Speaker> AllSpeaker = speakerService.selectAll();
        model.addAttribute("speakerList", AllSpeaker);

        return "/behind/videoList.jsp";
    }
    @RequestMapping("edit")
    public String editById(int id,Model model){
        Video video = videoService.selectById(id);
        model.addAttribute("video",video);

        List<Course> allCourse = courseService.findAll();
        model.addAttribute("courseList", allCourse);
        List<Speaker> AllSpeaker = speakerService.selectAll();
        model.addAttribute("speakerList", AllSpeaker);

        return "/behind/addVideo.jsp";
    }

    @RequestMapping("addVideo")
    public String addVideo(Video video, Model model) {
        model.addAttribute("video",video);
        List<Course> allCourse = courseService.findAll();
        model.addAttribute("courseList", allCourse);
        List<Speaker> AllSpeaker = speakerService.selectAll();
        model.addAttribute("speakerList", AllSpeaker);

        return "/behind/addVideo.jsp";
    }

    @RequestMapping("saveOrUpdate")
    public String  saveOrUpdate(Video video){
        videoService.saveOrUpdate(video);
        return "redirect:/video/list";
    }
    @ResponseBody
    @RequestMapping("videoDel")
    public String videoDel(int id) {
        String s = videoService.videoDel(id);
        return s;
    }
    @RequestMapping("delBatchVideos")
    public String delBatchVideos(String [] ids) {
        for (int i = 0; i < ids.length; i++) {
            videoService.videoDel(Integer.parseInt(ids[i]));
        }
        return  "redirect:/video/list";
    }

    @RequestMapping("showVideo")
    public  String showVideo(int videoId ,String subjectName,Model model) {
        List<Course> courseList = courseService.findAll();

        List<Subject> subjectList = subjectService.getAll();
        Video video = videoService.selectById(videoId);
        video.setPlayNum(video.getPlayNum()+1);
        videoService.updateById(video);

        int courseId = video.getCourseId();

        Course course = courseService.findByid(courseId);
        List<Video> videoList = course.getVideoList();

        model.addAttribute("courseList",courseList);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("video",video);

        model.addAttribute("subjectName",subjectName);
        model.addAttribute("course",course);
        return  "/before/section.jsp";

    }


    @RequestMapping("updatePlayNum")
    @ResponseBody
    public void updatePlayNum(int id , int playNum ) {


        Video video = videoService.selectById(id);

        videoService.updateById(video);

    }
}
