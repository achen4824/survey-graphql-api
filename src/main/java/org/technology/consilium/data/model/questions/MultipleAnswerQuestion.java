package org.technology.consilium.data.model.questions;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class MultipleAnswerQuestion extends Question{

    @ElementCollection
    private List<String> values;

    @OneToMany(orphanRemoval = true)
    public List<Question> subQuestions;
}
