package org.technology.consilium.data.model.questions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class BinaryQuestion extends Question {

    private Boolean isYes;

    @OneToMany(orphanRemoval = true)
    private List<Question> yesQuestion;


    @OneToMany(orphanRemoval = true)
    private List<Question> noQuestion;

}
