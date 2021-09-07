package com.pc.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/r")
public class ResourceController {
    @GetMapping(value = "/r1")
    @PreAuthorize("hasAnyRole('ROLE_1')")
    public String r1() {
        return "访问资源1";
    }

    @GetMapping(value = "/r2")
    @PreAuthorize("hasAnyAuthority('p2')")
    public String r2() {
        return "访问资源2";
    }

}
