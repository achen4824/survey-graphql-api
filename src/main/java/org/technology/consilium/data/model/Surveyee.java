package org.technology.consilium.data.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class Surveyee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID uniqueId;

    @NotBlank
    private String name;

    @NotBlank
    private String company;

    private String email;

    private String mobile;

    private String officePhone;

    private String contactName;

    private State state;

    @NonNull
    private Gender gender;

    @NotBlank
    private String ageRange;

    @OneToMany(mappedBy = "surveyee",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Survey> surveys  = new ArrayList<>();

    public Surveyee() {
        uniqueId = UUID.randomUUID();
    }

}
