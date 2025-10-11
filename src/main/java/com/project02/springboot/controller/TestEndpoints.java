package com.project02.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndpoints {
    
    @GetMapping("/")
    public String root() {
        return "NBA Teams API is running! Visit /api/teams to get all teams.";
    }


}
