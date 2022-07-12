package org.technology.consilium.data.model.questions;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
public class RatingQuestion extends Question{

    @Min(0)
    @Max(10)
    private Integer score;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "good_question_id", referencedColumnName = "id")
    private Question goodQuestion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "neutral_question_id", referencedColumnName = "id")
    private Question neutralQuestion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "improvement_question_id", referencedColumnName = "id")
    private Question improvementQuestion;
}
