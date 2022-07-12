package org.technology.consilium.data.wiring;

import graphql.schema.DataFetcher;
import graphql.schema.idl.EnumValuesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.technology.consilium.data.model.Gender;
import org.technology.consilium.data.model.State;
import org.technology.consilium.data.model.Survey;
import org.technology.consilium.data.model.Surveyee;
import org.technology.consilium.data.repositories.SurveyeeRepository;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

@Component
public class SurveyeeWiring {
    public final SurveyeeRepository surveyeeRepository;

    @Autowired
    public SurveyeeWiring(SurveyeeRepository surveyeeRepository) {
        this.surveyeeRepository = surveyeeRepository;
    }

    public SurveyeeRepository getSurveyeeRepository() {
        return surveyeeRepository;
    }

    public DataFetcher<List<Surveyee>> surveyeeDataFetcher = environment -> {
      List<Surveyee> surveyees = new ArrayList<>();
      getSurveyeeRepository().findAll().forEach(surveyees::add);
      return surveyees;
    };

    public DataFetcher<List<Survey>> surveysDataFetcher = environment -> {
        Surveyee surveyee = environment.getSource();
        return surveyee.getSurveys();
    };

    public EnumValuesProvider genderResolver = Gender::valueOf;
    public EnumValuesProvider stateResolver = State::valueOf;
}
