package com.softwarecafe.springSecurityTutorial.service;

import com.softwarecafe.springSecurityTutorial.model.UserInfo;
import com.softwarecafe.springSecurityTutorial.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserInfo addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return userInfo;
    }
}
