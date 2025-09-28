package com.project02.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestEndpoints {
    
    @GetMapping("/test")
    public String health() {
        return "Backend is running!";
    }

}
