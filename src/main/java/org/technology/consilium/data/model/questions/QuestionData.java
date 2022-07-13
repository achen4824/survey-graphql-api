package org.technology.consilium.data.model.questions;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.technology.consilium.data.model.Survey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QuestionData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected UUID uniqueID;

    @Column(columnDefinition = "TEXT")
    protected String queryString;

    protected String category;

    public QuestionData() {
        uniqueID = UUID.randomUUID();
    }

    public QuestionData(String queryString, String category) {
        this();
        this.queryString = queryString;
        this.category = category;
    }
}
