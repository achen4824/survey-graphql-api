package org.technology.consilium.data.model.questions;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.technology.consilium.data.model.Survey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QuestionData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(columnDefinition = "TEXT")
    protected String queryString;

    protected String category;

    public QuestionData(String queryString, String category) {
        this.queryString = queryString;
        this.category = category;
    }
}
