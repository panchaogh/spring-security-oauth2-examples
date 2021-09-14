package com.pc.demo.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pc.demo.model.entity.User;
import com.pc.demo.model.entity.UserRole;
import com.pc.demo.service.UserRoleService;
import com.pc.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        Optional<User> userOptional = userService.getByUniqueKey(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        User user = userOptional.get();
        //查询用户角色列表
        LambdaQueryWrapper<UserRole> queryWrapper = Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId());
        String[] authorities = userRoleService.list(queryWrapper).stream().map(userRole -> "ROLE_" + userRole.getId()).toArray(String[]::new);
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(authorities)
            .build();
    }
}
