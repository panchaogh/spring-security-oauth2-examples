package com.pc.demo.controller;

import com.pc.demo.model.dto.UserDTO;
import com.pc.demo.model.param.AuthenticationRequest;
import com.pc.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 用户登录
     * @param authenticationRequest 登录请求
     * @return
     */
    @PostMapping(value = "/login")
    public String login(AuthenticationRequest authenticationRequest, HttpSession session) {
        UserDTO userDTO = null;
        try {
            userDTO = authenticationService.authentication(authenticationRequest);
        } catch (Exception e) {
            return "登陆失败：" + e.getMessage();
        }
        session.setAttribute(UserDTO.SESSION_USER_KEY, userDTO);
        return userDTO.getFullname() + " 登录成功";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "退出成功";
    }

    /**
     * 测试资源1
     * @param session
     * @return
     */
    @GetMapping(value = "/r/r1")
    public String r1(HttpSession session) {
        String fullname = null;
        Object userObj = session.getAttribute(UserDTO.SESSION_USER_KEY);
        if (userObj != null) {
            fullname = ((UserDTO) userObj).getFullname();
        } else {
            fullname = "匿名";
        }
        return fullname + " 访问资源1";
    }

    @GetMapping(value = "/r/r2")
    public String r2(HttpSession session) {
        String fullname = null;
        Object userObj = session.getAttribute(UserDTO.SESSION_USER_KEY);
        if (userObj != null) {
            fullname = ((UserDTO) userObj).getFullname();
        } else {
            fullname = "匿名";
        }
        return fullname + " 访问资源2";
    }


}
