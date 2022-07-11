package org.technology.consilium.data.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class QuestionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String query_;

    private String category;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyTemplate surveyTemplate;
}
