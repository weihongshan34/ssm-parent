package com.qf;

import com.qf.pojo.User;

public interface UserService {
    String findByUser(User user);
    User findByEmail(String email);
    String insertUser(User user);
    String IsExit(User user);
    void updateImgurl(User user);
    public void updateUser(User user);
}
