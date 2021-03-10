package com.qf.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.CourseService;
import com.qf.SpeakerService;
import com.qf.VideoService;
import com.qf.dao.SpeakerMapper;
import com.qf.dao.VideoMapper;
import com.qf.pojo.QueryVo;
import com.qf.pojo.Speaker;
import com.qf.pojo.Video;
import com.qf.pojo.VideoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service
public class VideoSeriveImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private SpeakerService speakerService;
    @Autowired
    private CourseService courseService;

    public PageInfo findAllByPage(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getPage(), queryVo.getSize());

        VideoExample videoExample = new VideoExample();

        VideoExample.Criteria criteria = videoExample.createCriteria();

        if (queryVo.getTitle() != null ) {
            criteria.andTitleLike("%" + queryVo.getTitle() + "%");
        }

        if (queryVo.getSpeakerId() != "" && queryVo.getSpeakerId() != null) {
            criteria.andSpeakerIdEqualTo(Integer.parseInt(queryVo.getSpeakerId()));
        }

        if (queryVo.getCourseId() != "" && queryVo.getCourseId() != null) {
            criteria.andCourseIdEqualTo(Integer.parseInt(queryVo.getCourseId()));
        }

        List<Video> videos = videoMapper.selectByExample(videoExample);
        System.out.println(videos);
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i).getSpeakerId()!=null){

                Speaker speaker = speakerService.selectSpeakerById(videos.get(i).getSpeakerId());
                videos.get(i).setSpeaker(speaker);
            }
        }
        System.out.println(videos);
        PageInfo<Video> videoPageInfo = new PageInfo<Video>(videos);


        return videoPageInfo;
    }

    public Video selectById(int id) {

        Video video = videoMapper.selectByPrimaryKey(id);

        Speaker speaker = speakerService.selectSpeakerById(video.getSpeakerId());
        video.setSpeaker(speaker);
        return video;

    }

    public void saveOrUpdate(Video video) {
        if (video.getId() != null) {
            videoMapper.updateByPrimaryKey(video);
        } else {
            videoMapper.insert(video);
        }
    }

    public String videoDel(int id) {
        int i = videoMapper.deleteByPrimaryKey(id);
        if (i!=0){
            return "success";
        }else {
            return  "faile";
        }
    }

    public List<Video> findByCid(int cid) {
        List<Video> videos = videoMapper.selectByCid(cid);
        return videos;
    }

    public void updateById(Video video) {
        videoMapper.updateByPrimaryKey(video);
    }


}
