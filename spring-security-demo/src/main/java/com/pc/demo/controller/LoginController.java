package com.pc.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/login-success")
    public String loginSuccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return "未认证";
        }
        Object principal = authentication.getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        }else {
            username = principal.toString();
        }
        return username + "登录成功";
    }

    /**
     * 测试资源1
     * @return
     */
    @GetMapping(value = "/r/r1")
    public String r1() {
        return "访问资源1";
    }

    @GetMapping(value = "/r/r2")
    public String r2() {
        return "访问资源2";
    }


}
