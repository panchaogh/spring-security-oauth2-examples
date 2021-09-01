package com.pc.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class ApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPasswordEncoder(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

}
