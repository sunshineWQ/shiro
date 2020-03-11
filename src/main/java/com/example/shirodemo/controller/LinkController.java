package com.example.shirodemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.RequestWrapper;

@Controller
public class LinkController {
    @RequestMapping("goindex")
    public String userManage(){
        return "index";
    }

    @RequestMapping("gologin")
    public String login(){
        return "login";
    }
    @RequestMapping("gohello")
    public String gohello(){
        return "hello";
    }
    @RequestMapping("noAuth")
    public String noAuth(){
        return "noAuth";
    }
    @RequestMapping("gouserInfo")
    public String userInfo(){
        return "userInfo";
    }
}
