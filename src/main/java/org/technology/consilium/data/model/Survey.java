package org.technology.consilium.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.technology.consilium.data.model.questions.Question;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Surveyee surveyee;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Surveyor surveyor;

    @NotBlank
    private String date;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private SurveyTemplate survey;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<Question> questions  = new ArrayList<>();

}
