package org.technology.consilium.data.model.questions;


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
public class QuestionData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(columnDefinition = "TEXT")
    protected String queryString;

    protected String category;

    public Long getId() {
        return (long) (queryString.hashCode() + category.hashCode());
    }

    public QuestionData(String queryString, String category) {
        this.queryString = queryString;
        this.category = category;
    }
}
