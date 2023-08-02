package com.softwarecafe.springSecurityTutorial.repository;

import com.softwarecafe.springSecurityTutorial.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUsername(String username);
}