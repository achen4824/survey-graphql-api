package org.technology.consilium.data.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_template_id", referencedColumnName = "id")
    private QuestionTemplate query_;

    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;

    @Min(0)
    @Max(10)
    private Integer score;

    private String comment;

    private Boolean isYes;
}
