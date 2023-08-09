package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build login rest api
    @PostMapping(value = {"login","sign-in"})
    public ResponseEntity<ObjectNode> login(@RequestBody LoginDto loginDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        var objectNode = objectMapper.createObjectNode();
        objectNode.put("message", authService.login(loginDto));
        return ResponseEntity.ok(objectNode);
    }
    // Build Register rest api
    @PostMapping(value = {"register","sign-up"})
    public ResponseEntity<ObjectNode> register(@RequestBody RegisterDto registerDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        var objectNode = objectMapper.createObjectNode();
        objectNode.put("message", authService.register(registerDto));
        return ResponseEntity.ok(objectNode);
    }


}
