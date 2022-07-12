package org.technology.consilium.data.model.questions;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class NumberQuestion extends Question{

    private Integer value;

}
