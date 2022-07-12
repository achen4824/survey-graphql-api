package org.technology.consilium.data.model.questions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
public class NPSQuestion extends Question{

    @Min(0)
    @Max(10)
    private Integer score;

    private String comment;
}
