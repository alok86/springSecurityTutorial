package com.softwarecafe.springSecurityTutorial.repository;

import com.softwarecafe.springSecurityTutorial.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Questions, Long> {
}