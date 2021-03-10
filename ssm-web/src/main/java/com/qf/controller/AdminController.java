package com.qf.controller;

import com.qf.Adminservice;
import com.qf.impl.AdminServiceImpl;
import com.qf.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private Adminservice adminservice;
    @RequestMapping("login")
    @ResponseBody
    public String login(Admin admin , HttpSession session) {
        String login = adminservice.login(admin);
        if ("success".equals(login)){
            session.setAttribute("userName",admin.getUsername());
            session.setAttribute("admin",admin);
        }
        return login;
    }
    @RequestMapping("exit")
    public String exit(HttpSession session) {
       session.invalidate();
        return  "/behind/login.jsp";

    }
    @RequestMapping("loginView")
    public String loginView(HttpSession session) {
        session.invalidate();
        return  "/behind/login.jsp";

    }
}
