package com.qf.impl;

import com.qf.UserService;
import com.qf.dao.UserMapper;
import com.qf.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    public String findByUser(User user) {
        User byName = userMapper.findByName(user.getEmail());
      
        if (byName==null){
            return "false";
        }
        if (user.getPassword().equals(byName.getPassword())){
            return "success";
        }else {
            return "false";
        }
    }

    public User findByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        User byName = userMapper.findByName(email);
        return byName;
    }

    public String insertUser(User user) {
        int insert = userMapper.insert(user);
        if (insert!=0){
            return "success";
        }else {
            return "false";
        }
    }
    public String IsExit(User user) {
        User byName = userMapper.findByName(user.getEmail());
        if (byName==null){
            return "success";
        }else {
            return "false";
        }

    }

    public void updateImgurl(User user) {
        userMapper.updateByPrimaryKey(user);
    }
    public void updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
    }



}
