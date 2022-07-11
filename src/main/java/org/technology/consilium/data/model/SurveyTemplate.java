package org.technology.consilium.data.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class SurveyTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "surveyTemplate",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<QuestionTemplate> questions = new ArrayList<>();

    @OneToMany(mappedBy = "survey",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Survey> surveysAdministered = new ArrayList<>();
}
