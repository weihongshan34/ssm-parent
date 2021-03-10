package com.qf.controller;

import com.qf.SubjectService;
import com.qf.UserService;
import com.qf.dao.CourseMapper;
import com.qf.pojo.Course;
import com.qf.pojo.Subject;
import com.qf.pojo.User;
import com.qf.videos.utils.ImageCut;
import com.qf.videos.utils.MailUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private UserService userService;

    @RequestMapping("view")
    public String getAll(Model model) {
        List<Subject> all = subjectService.getAll();
        model.addAttribute("subjectList",all);
        return "/before/index.jsp";
    }

    @RequestMapping("loginUser")
    @ResponseBody
    public String loginUser(User user,HttpSession session){

        String result = userService.findByUser(user);

        session.setAttribute("email",user.getEmail());

        return result;
    }

    @RequestMapping("insertUser")
    @ResponseBody
    public String insertUser(User user, HttpSession session){

        String s = userService.insertUser(user);

        session.setAttribute("email",user.getEmail());

        return s;
    }
    @RequestMapping("validateEmail")
    @ResponseBody
    public String validateEmail(User user) {
        String s = userService.IsExit(user);
        return s;
    }
    @RequestMapping("showMyProfile")
    public String showMyProfile (HttpSession session, Model model) {
        Object s = session.getAttribute("email");
        String email = s.toString();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        session.setAttribute("Admin",user);
        return "/before/my_profile.jsp";
    }
    @RequestMapping("changeAvatar")
    public String changeAvatar(HttpSession session,Model model) {
        User admin = (User)session.getAttribute("Admin");

        model.addAttribute("user",admin);
        return  "/before/change_avatar.jsp";
    }
    @RequestMapping("upLoadImage")
    public String upLoadImage(@RequestParam("image_file") MultipartFile photo,
                              HttpSession session, HttpServletRequest request,
                              String x1, String x2, String y1, String y2) throws IOException {


        String path = "D:\\apache-tomcat-8.5.47\\webapps\\upload";

        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = simpleDateFormat.format(d);

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = photo.getOriginalFilename();
        filename = filename.substring(filename.lastIndexOf("."));
        filename = date + filename;
        photo.transferTo(new File(path, filename));
        int x1Int = (int) Double.parseDouble(x1);
        int x2Int = (int) Double.parseDouble(x2);
        int y1Int = (int) Double.parseDouble(y1);
        int y2Int = (int) Double.parseDouble(y2);
        new ImageCut().cutImage(path + "/" + filename, x1Int, y1Int, x2Int - x1Int, y2Int - y1Int);
        User user = (User) session.getAttribute("Admin");

        user.setImgurl(filename);
        userService.updateUser(user);
        return "redirect:/user/showMyProfile";


    }
    @RequestMapping("loginOut2")
    public String loginOut2(HttpSession session) {
        session.invalidate();
        return "redirect:/user/view";

    }
    @ResponseBody
    @RequestMapping("loginOut")
    public void loginOut(HttpSession session) {
        session.invalidate();

    }
    @RequestMapping("forgetPassword")
    public String forgetPassword(Model model ,String email){

        model.addAttribute("email",email);

        return "/before/forget_password.jsp";

    }
    @RequestMapping("changeProfile")
    public String changeProfile(HttpSession session,Model model) {
        User admin =(User) session.getAttribute("Admin");
        model.addAttribute("user",admin);
        return "/before/change_profile.jsp";
    }
    @RequestMapping("updateUser")
    public String updateUser(User newUser ,HttpSession session) {
        User olduser =(User) session.getAttribute("Admin");

        olduser.setAddress(newUser.getAddress());
        olduser.setBirthday(newUser.getBirthday());
        olduser.setNickname(newUser.getNickname());
        olduser.setSex(newUser.getSex());
        userService.updateUser(olduser);

        session.setAttribute("Admin",olduser);
        return "redirect:/user/showMyProfile";
    }
    @ResponseBody
    @RequestMapping("sendEmail")
    public String  sendEmail(String Mail ,HttpSession session) {
        User user = new User();
        user.setEmail(Mail);
        String s = userService.IsExit(user);
        if("success".equals(s)) {
            return "hasNoUser";
        }
        String random = MailUtils.getValidateCode(6);

        session.setAttribute("code",random);
        MailUtils.sendMail(Mail,"你好，这是一封测试邮件，无需回复。", "测试邮件随机生成的验证码是：" + random);
        return "success";
    }

    @RequestMapping("validateEmailCode")
    public String validateEmailCode(String email ,String code ,HttpSession session,Model model) {
        String random = (String) session.getAttribute("code");
        model.addAttribute("email",email);
        if(code.equals(random)) {
            return "/before/reset_password.jsp";
        }else {
            return "redirect:/user/forgetPassword";
        }
    }
    @RequestMapping("resetPassword")
    public String resetPassword(User user,HttpSession session) {
        User admin = userService.findByEmail(user.getEmail());

        admin.setPassword(user.getPassword());

        userService.updateUser(admin);

        return "/before/index.jsp";
    }
    @RequestMapping("passwordSafe")
    public String passwordSafe(HttpSession session,Model model) {
        String email = (String) session.getAttribute("email");
        User user = userService.findByEmail(email);
        session.setAttribute("Admin",user);
        model.addAttribute("user",user);
        return"/before/password_safe.jsp";
    }
    @RequestMapping("validatePassword")
    @ResponseBody
    public String validatePassword(String password,HttpSession session) {
        User admin =(User) session.getAttribute("Admin");

        if (admin.getPassword().equals(password)){
            return "success";
        }else {
            return "false";
        }
    }


    @RequestMapping("updatePassword")
    public String updatePassword(String oldPassword ,String newPassword,HttpSession session) {
        User admin =(User) session.getAttribute("Admin");
        if (admin.getPassword().equals(oldPassword)){
            admin.setPassword(newPassword);
            userService.updateUser(admin);
            session.setAttribute("Admin",admin);

        }
        return"redirect:/user/showMyProfile";
    }

}
