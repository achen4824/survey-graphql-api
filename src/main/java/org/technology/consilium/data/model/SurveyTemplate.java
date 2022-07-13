package org.technology.consilium.data.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "survey",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Survey> surveysAdministered = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public String toString() {
        return id.toString() + questions.toString();
    }
}
