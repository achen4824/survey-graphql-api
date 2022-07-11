package org.technology.consilium.data.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Surveyor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "surveyor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Survey> surveysAdministered  = new ArrayList<>();

}
