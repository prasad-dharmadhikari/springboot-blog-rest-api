package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.blog.payload.JwtAuthResponseDto;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@Tag(name = "REST APIs for Authentication and Registration Purpose")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build login rest api
    @Operation(
            summary = "Login REST API",
            description = "Use this API to login to the application"
    )
    @PostMapping(value = {"login","sign-in"})
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponseDto jwtAuthResponseDto = new JwtAuthResponseDto();
        jwtAuthResponseDto.setAccessToken(authService.login(loginDto));
        return ResponseEntity.ok(jwtAuthResponseDto);
    }

    // Build Register rest api
    @Operation(
            summary = "Register REST API",
            description = "Use this API to register to the application if you are new to the application"
    )
    @PostMapping(value = {"register","sign-up"})
    public ResponseEntity<ObjectNode> register(@RequestBody RegisterDto registerDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        var objectNode = objectMapper.createObjectNode();
        objectNode.put("message", authService.register(registerDto));
        return ResponseEntity.ok(objectNode);
    }


}
