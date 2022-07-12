package org.technology.consilium.data.wiring;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.technology.consilium.data.model.questions.Question;
import org.technology.consilium.data.model.Survey;
import org.technology.consilium.data.model.SurveyTemplate;
import org.technology.consilium.data.model.Surveyee;
import org.technology.consilium.data.repositories.SurveyRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class SurveyWiring {

    public final SurveyRepository surveyRepository;

    @Autowired
    public SurveyWiring(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public SurveyRepository getSurveyRepository() {
        return surveyRepository;
    }

    public DataFetcher<List<Survey>> surveyDataFetcher = environment -> {
        List<Survey> surveys = new ArrayList<>();
        getSurveyRepository().findAll().forEach(surveys::add);
        return surveys;
    };

    public DataFetcher<Surveyee> surveyeeDataFetcher = environment -> {
        Survey survey = environment.getSource();
        return survey.getSurveyee();
    };

    public DataFetcher<SurveyTemplate> surveyTemplateDataFetcher = environment -> {
        Survey survey = environment.getSource();
        return survey.getSurvey();
    };

    public DataFetcher<List<Question>> questionsDataFetcher = environment -> {
        Survey survey = environment.getSource();
        return survey.getQuestions();
    };
}
