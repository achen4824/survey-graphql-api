package org.technology.consilium.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class NPSQuestion extends Question{

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_template_id", referencedColumnName = "id")
    private NPSQuestionTemplate query_;

    @Getter
    @Setter
    @Min(0)
    @Max(10)
    private Integer score;

    @Getter
    @Setter
    private String comment;
}
