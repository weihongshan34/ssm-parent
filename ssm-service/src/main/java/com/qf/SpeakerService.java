package com.qf;

import com.qf.pojo.Speaker;

import java.util.List;

public interface SpeakerService {
    List<Speaker> selectAll();
    Speaker selectSpeakerById(int id);
    void saveOrUpdate(Speaker speaker);
    String delSpeakerById(int id);
}
