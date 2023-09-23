package com.softwarecafe.springSecurityTutorial.controller;

import com.softwarecafe.springSecurityTutorial.dto.AuthRequest;
import com.softwarecafe.springSecurityTutorial.dto.JwtResponse;
import com.softwarecafe.springSecurityTutorial.dto.RefreshTokenRequest;
import com.softwarecafe.springSecurityTutorial.model.RefreshToken;
import com.softwarecafe.springSecurityTutorial.model.UserInfo;
import com.softwarecafe.springSecurityTutorial.service.JwtService;
import com.softwarecafe.springSecurityTutorial.service.RefreshTokenService;
import com.softwarecafe.springSecurityTutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private RefreshTokenService refreshTokenService;
    @PostMapping("/new")
    public String addUser(@RequestBody UserInfo userInfo){
        UserInfo info = userService.addUser(userInfo);
        return "user added successfully "+info;
    }
    @PostMapping("/authentication")
    public JwtResponse authenticationAndToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserName(),authRequest.getPassword()
                ));
        if (authentication.isAuthenticated())
        {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUserName());

             return JwtResponse.builder()
                     .accessToken(jwtService.generateToken(authRequest.getUserName()))
                     .token(refreshToken.getToken()).build();
        }
        else {
            throw new UsernameNotFoundException("Invalid user or password");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUsername());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(()->new RuntimeException("Refresh token is not in database"));
    }
}
