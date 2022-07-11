package org.technology.consilium.data.wiring;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.technology.consilium.data.model.Survey;
import org.technology.consilium.data.model.Surveyor;
import org.technology.consilium.data.repositories.SurveyorRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class SurveyorWiring {

    public final SurveyorRepository surveyorRepository;

    @Autowired
    public SurveyorWiring(SurveyorRepository surveyorRepository) {
        this.surveyorRepository = surveyorRepository;
    }

    public SurveyorRepository getSurveyorRepository() {
        return surveyorRepository;
    }

    public DataFetcher<List<Surveyor>> surveyorDataFetcher = environment -> {
        List<Surveyor> surveyors = new ArrayList<>();
        getSurveyorRepository().findAll().forEach(surveyors::add);
        return surveyors;
    };

    public DataFetcher<List<Survey>> surveysAdministered = environment -> {
        Surveyor surveyor = environment.getSource();
        return surveyor.getSurveysAdministered();
    };


}
