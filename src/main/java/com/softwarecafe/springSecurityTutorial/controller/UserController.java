package com.softwarecafe.springSecurityTutorial.controller;

import com.softwarecafe.springSecurityTutorial.model.UserInfo;
import com.softwarecafe.springSecurityTutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userservice")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/new")
    public String addUser(@RequestBody UserInfo userInfo){
        UserInfo info = userService.addUser(userInfo);
        return "user added successfully "+info;
    }
}
