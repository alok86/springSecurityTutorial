package com.softwarecafe.springSecurityTutorial.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "questions")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Questions {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "answer_1", length = 100)
    private String answer1;

    @Column(name = "answer_2", length = 100)
    private String answer2;

    @Column(name = "answer_3", length = 100)
    private String answer3;

    @Column(name = "answer_4", length = 100)
    private String answer4;

    @Column(name = "answer_right", length = 100)
    private String answerRight;

    @Column(name = "description")
    private String description;

}