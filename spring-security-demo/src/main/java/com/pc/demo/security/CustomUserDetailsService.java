package com.pc.demo.security;

import com.pc.demo.dao.UserDao;
import com.pc.demo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(userDao.findRolesByUserId(user.getId()).stream().map(String::valueOf).toArray(String[]::new))
            .build();
    }
}
