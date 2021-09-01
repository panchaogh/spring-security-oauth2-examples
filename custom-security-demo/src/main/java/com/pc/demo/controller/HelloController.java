package com.pc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public ModelAndView hello(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", "Hello World!");
        modelAndView.setViewName("/hello");
        return modelAndView;
    }
}
