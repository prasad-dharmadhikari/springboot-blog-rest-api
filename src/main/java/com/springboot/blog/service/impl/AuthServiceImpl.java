package com.springboot.blog.service.impl;

import com.springboot.blog.configuration.ModelMapperConfiguration;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapperConfiguration().getModelMapper();
    }

    @Override
    public String login(LoginDto loginDto) {
        final var authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken
                        (loginDto.getUsernameOrEmail(), loginDto.getPassword())
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User Logged in successfully......";
    }

    @Override
    public String register(RegisterDto registerDto) {
        // check for username exists or not
        if(userRepository.existsByUserName(registerDto.getUserName())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "User name already exists");
        }
        // check for email exists or not
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        final var userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);


        return "User Registered Successfully";
    }
}
