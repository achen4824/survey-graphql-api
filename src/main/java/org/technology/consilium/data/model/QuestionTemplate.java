package org.technology.consilium.data.model;


import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class QuestionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String query_;

    @NonNull
    private QuestionType questionType;

    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyTemplate surveyTemplate;
}
