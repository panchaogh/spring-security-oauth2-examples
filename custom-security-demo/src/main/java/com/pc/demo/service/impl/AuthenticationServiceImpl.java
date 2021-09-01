package com.pc.demo.service.impl;

import com.pc.demo.model.dto.UserDTO;
import com.pc.demo.model.param.AuthenticationRequest;
import com.pc.demo.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    //用户信息
    private Map<String,UserDTO> userMap = new HashMap<>();
    {
        userMap.put("zhangsan",UserDTO.builder().id("1010").username("zhangsan").password("123").fullname("张三").mobile("133443")
            .authorities(new HashSet<String>(){{add("p1");}})
            .build());
        userMap.put("lisi",UserDTO.builder().id("1011").username("lisi").password("456").fullname("李四").mobile("144553")
            .authorities(new HashSet<String>(){{add("p2");}})
            .build());
    }
    @Override
    public UserDTO authentication(AuthenticationRequest authenticationRequest) {
        if(authenticationRequest == null
            || StringUtils.isEmpty(authenticationRequest.getUsername())
            || StringUtils.isEmpty(authenticationRequest.getPassword())){
            throw new RuntimeException("账号或密码为空");
        }
        UserDTO userDTO = getUserDTO(authenticationRequest.getUsername());
        if(userDTO == null){
            throw new RuntimeException("查询不到该用户");
        }
        if(!authenticationRequest.getPassword().equals(userDTO.getPassword())){
            throw new RuntimeException("账号或密码错误");
        }
        return userDTO;

    }

    //模拟用户查询
    public UserDTO getUserDTO(String username){
        return userMap.get(username);
    }
}
