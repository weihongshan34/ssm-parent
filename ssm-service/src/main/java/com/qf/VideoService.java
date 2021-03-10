package com.qf;

import com.github.pagehelper.PageInfo;
import com.qf.pojo.QueryVo;
import com.qf.pojo.Video;

import java.util.List;

public interface VideoService {
    public PageInfo findAllByPage(QueryVo queryVo);
    public Video selectById(int id);
    public void saveOrUpdate(Video video);
    public String videoDel(int id);
    public List<Video> findByCid(int cid);
    public void updateById(Video video);
}
