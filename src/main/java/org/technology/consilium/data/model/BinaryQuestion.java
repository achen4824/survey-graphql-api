package org.technology.consilium.data.model;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class BinaryQuestion extends Question{

    @Getter
    @Setter
    private Boolean isYes;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_template_id", referencedColumnName = "id")
    private BinaryQuestionTemplate query_;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "yes_question_id", referencedColumnName = "id")
    private NPSQuestion yesQuestion;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "no_question_id", referencedColumnName = "id")
    private NPSQuestion noQuestion;

}
