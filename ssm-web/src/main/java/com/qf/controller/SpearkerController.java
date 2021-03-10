package com.qf.controller;

import com.qf.SpeakerService;
import com.qf.pojo.Speaker;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("speaker")
public class SpearkerController {
    @Autowired
    private SpeakerService speakerService;
    @RequestMapping("showSpeakerList")
    public String showSpeakerList(Model model) {
        List<Speaker> speakers = speakerService.selectAll();
        model.addAttribute("speakers",speakers);
        return "/behind/speakerList.jsp";
    }
    @RequestMapping("addSpeaker")
    public String addSpeaker() {
        return  "/behind/addSpeaker.jsp";
    }
    @RequestMapping("edit")
    public String edit(int id,Model model) {
        Speaker speaker = speakerService.selectSpeakerById(id);
        model.addAttribute("speaker",speaker);
        return  "/behind/addSpeaker.jsp";
    }
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Speaker speaker) {
        speakerService.saveOrUpdate(speaker);
        return "redirect:/speaker/showSpeakerList";
    }
    @ResponseBody
    @RequestMapping("speakerDel")
    public String speakerDel(int id) {
        String s = speakerService.delSpeakerById(id);
        return s;
    }

}
