package com.pc.demo.service;


import com.pc.demo.model.dto.UserDTO;
import com.pc.demo.model.param.AuthenticationRequest;

public interface AuthenticationService {
    /**
     * 用户认证
     */
    UserDTO authentication(AuthenticationRequest authenticationRequest);
}
