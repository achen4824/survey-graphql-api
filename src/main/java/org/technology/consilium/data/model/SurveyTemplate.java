package org.technology.consilium.data.model;

import lombok.Data;
import org.technology.consilium.data.model.questions.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SurveyTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "survey",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Survey> surveysAdministered = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
    }
}
