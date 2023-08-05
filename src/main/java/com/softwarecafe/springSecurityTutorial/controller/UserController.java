package com.softwarecafe.springSecurityTutorial.controller;

import com.softwarecafe.springSecurityTutorial.dto.AuthRequest;
import com.softwarecafe.springSecurityTutorial.model.UserInfo;
import com.softwarecafe.springSecurityTutorial.service.JwtService;
import com.softwarecafe.springSecurityTutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userservice")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/new")
    public String addUser(@RequestBody UserInfo userInfo){
        UserInfo info = userService.addUser(userInfo);
        return "user added successfully "+info;
    }
    @PostMapping("/authentication")
    public String authenticationAndToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserName(),authRequest.getPassword()
                ));
        if (authentication.isAuthenticated())
        {
            return jwtService.generateToken(authRequest.getUserName());
        }
        else {
            throw new UsernameNotFoundException("Invalid user or password");
        }
    }
}
