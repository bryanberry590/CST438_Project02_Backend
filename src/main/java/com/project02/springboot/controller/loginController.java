package com.project02.springboot.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

  @GetMapping("/public/ping")
  public Map<String,String> ping(){ return Map.of("status","ok"); }

  @GetMapping("/me")
  public Map<String,Object> me(@AuthenticationPrincipal OidcUser user){
    return Map.of(
      "sub", user.getSubject(),
      "name", user.getFullName(),
      "email", user.getEmail(),
      "picture", user.getPicture()
    );
  }
}
