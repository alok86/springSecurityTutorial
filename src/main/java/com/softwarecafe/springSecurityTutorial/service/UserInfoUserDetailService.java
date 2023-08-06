package com.softwarecafe.springSecurityTutorial.service;

import com.softwarecafe.springSecurityTutorial.model.UserInfo;
import com.softwarecafe.springSecurityTutorial.repository.UserInfoRepository;
import com.softwarecafe.springSecurityTutorial.util.UserInfoUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserInfoUserDetailService implements UserDetailsService {
    @Autowired
    private UserInfoRepository repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repo.findByUsername(username);
        return userInfo.map(UserInfoUserDetail::new)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"+username));

    }
}
