package org.technology.consilium.data.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class Surveyee {

    private UUID uniqueId;

    @NotBlank
    private String name;

    @NotBlank
    private String company;

    private String email;

    private String mobile;

    private State state;

    @NonNull
    private Gender gender;

    @NotBlank
    private String ageRange;

    List<Integer> surveyIds;

    Surveyee() {
        uniqueId = UUID.randomUUID();
    }

}
