package com.example.shirodemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.shirodemo.entity.User;
import com.example.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 管理员登录 shiro
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/adminLogin")
    public String userLogin(User user, HttpServletRequest request){
        //获取Subject
        Subject subject= SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUserphone(),user.getPassword());
        try {
            //验证登录信息
            subject.login(token);
            if(subject.isAuthenticated()){// 验证通过
                //从shiro的session中取出我们保存的对象object，该对象在登录认证成功后保存的
                Object object=subject.getPrincipal();
                User dbUser=null;
                if(object instanceof User){
                    dbUser=(User)object;
                }
                subject.getSession().setAttribute("user",dbUser);
                return "gohello";
            }else {
                token.clear();
                return "login";
            }
        } catch (UnknownAccountException e) {
            //登录失败:用户名不存在，UnknownAccountException是Shiro抛出的找不到用户异常
            System.out.println("无效的用户名！");
            request.setAttribute("fail","登录账号不存在");
            return "login";
            //return "noAuth";
        }catch (IncorrectCredentialsException e){
            //登录失败:密码错误，IncorrectCredentialsException是Shiro抛出的密码错误异常
            System.out.println("密码错误！");
            request.setAttribute("fail","密码错误，请重试");
            return "login";
            // return "noPass";
        }
    }


    @GetMapping("/getAllUser")
    @ResponseBody
    public String getAllUser(){
        List<User> list=userService.getAllUser();
        if(list.size()>0){
            return JSON.toJSONString(list);
        }else {
            return "";
        }
    }
    @PutMapping("/addUser")
    @ResponseBody
    public String addUser(User user){
        int num=userService.addUser(user);
        if(num>0){
            return "true";
        }else {
            return "false";
        }
    }
    @PostMapping("/updateUser")
    @ResponseBody
    public String updateUser(User user){
        int num=userService.updateUser(user);
        if(num>0){
            return "true";
        }else{
            return "false";
        }
    }
    @PostMapping("/delUserById")
    @ResponseBody
    public String delUserById(@RequestParam("userphone") String userphone){
        int num=userService.delUserById(userphone);
        System.out.println(num);
        if(num>0){
            return "true";
        }else {
            return "false";
        }
    }

    //退出登录
    @GetMapping("tologout")
    public String tologout(HttpServletRequest request){
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        return "login";
    }

}
