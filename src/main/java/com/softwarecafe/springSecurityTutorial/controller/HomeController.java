package com.softwarecafe.springSecurityTutorial.controller;

import com.softwarecafe.springSecurityTutorial.model.Questions;
import com.softwarecafe.springSecurityTutorial.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HomeController {
    @Autowired
    private QuestionService service;
    @GetMapping("/welcome")
    public String welcomepage(){
        return "Welcome.html";
    }

    @GetMapping("/questions")
    public List<Questions> getAllQuestion(){
        return service.allQuestions();
    }
    @GetMapping("/question/{id}")
    public Optional<Questions> findOne(@PathVariable Long id){
        return service.findOne(id);
    }
    @PostMapping("/question/new")
    public Questions insertQuestion(@RequestBody Questions questions)
    {
        service.saveQuestion(questions);
        return questions;
    }


}
