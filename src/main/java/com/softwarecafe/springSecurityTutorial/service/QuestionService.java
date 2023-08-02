package com.softwarecafe.springSecurityTutorial.service;

import com.softwarecafe.springSecurityTutorial.model.Questions;
import com.softwarecafe.springSecurityTutorial.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionsRepository repo;

    public List<Questions> allQuestions(){
        return repo.findAll();
    }

    public Optional<Questions> findOne(Long id)
    {
        return repo.findById(id);
    }


    public void saveQuestion(Questions questions) {
        repo.save(questions);
    }
}
