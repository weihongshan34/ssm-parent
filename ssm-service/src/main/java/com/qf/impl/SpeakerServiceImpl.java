package com.qf.impl;

import com.qf.SpeakerService;
import com.qf.dao.SpeakerMapper;
import com.qf.pojo.Speaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SpeakerServiceImpl implements SpeakerService {

    @Autowired
    private SpeakerMapper speakerMapper;
    public List<Speaker> selectAll() {
        List<Speaker> speakers = speakerMapper.selectByExample(null);
        return speakers;
    }
    public Speaker selectSpeakerById(int id) {
        Speaker speaker = speakerMapper.selectByPrimaryKey(id);
        return  speaker;
    }

    public void saveOrUpdate(Speaker speaker) {
        if (speaker.getId()!=null){
            speakerMapper.updateByPrimaryKey(speaker);
        }else{
            speakerMapper.insert(speaker);
        }
    }
    public String delSpeakerById(int id) {
        int i = speakerMapper.deleteByPrimaryKey(id);
        if (i!=0){
            return "success";
        }else {
            return "false";
        }
    }
}
