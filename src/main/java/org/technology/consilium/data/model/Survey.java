package org.technology.consilium.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Surveyee surveyee;

    @NotBlank
    private String date;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyTemplate survey;

    @ManyToOne(fetch = FetchType.LAZY)
    private Surveyor surveyor;

    @OneToMany(mappedBy = "survey",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Question> questions  = new ArrayList<>();

}
