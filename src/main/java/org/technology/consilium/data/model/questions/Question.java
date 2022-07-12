package org.technology.consilium.data.model.questions;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.technology.consilium.data.model.Survey;
import org.technology.consilium.data.model.SurveyTemplate;

import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String queryString;

    private String category;

    private String questionType;
}
