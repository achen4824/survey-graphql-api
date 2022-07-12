package org.technology.consilium.data.model.questions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public QuestionData(String queryString, String category) {
        this.queryString = queryString;
        this.category = category;
    }
}
