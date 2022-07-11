package org.technology.consilium.data.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class BinaryQuestionTemplate extends QuestionTemplate{

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "yes_question_template_id", referencedColumnName = "id")
    private NPSQuestionTemplate yesQuestion;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "no_question_template_id", referencedColumnName = "id")
    private NPSQuestionTemplate noQuestion;
}
