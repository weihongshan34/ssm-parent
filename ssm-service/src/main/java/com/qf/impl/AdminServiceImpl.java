package com.qf.impl;

import com.qf.Adminservice;
import com.qf.dao.AdminMapper;
import com.qf.dao.CourseMapper;
import com.qf.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements Adminservice {
    @Autowired
    private AdminMapper adminMapper;
    public String login(Admin admin) {
        Admin login = adminMapper.login(admin);
        if (login!=null){
            return "success";
        }else {
            return "false";
        }

    }
}
