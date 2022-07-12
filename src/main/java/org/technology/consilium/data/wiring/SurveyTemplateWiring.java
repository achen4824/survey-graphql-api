package org.technology.consilium.data.wiring;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.technology.consilium.data.model.Survey;
import org.technology.consilium.data.model.SurveyTemplate;
import org.technology.consilium.data.model.questions.Question;
import org.technology.consilium.data.repositories.SurveyTemplateRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class SurveyTemplateWiring {

    public final SurveyTemplateRepository surveyTemplateRepository;

    @Autowired
    public SurveyTemplateWiring(SurveyTemplateRepository surveyTemplateRepository) {
        this.surveyTemplateRepository = surveyTemplateRepository;
    }

    public SurveyTemplateRepository getSurveyTemplateRepository() {
        return surveyTemplateRepository;
    }

    public DataFetcher<List<SurveyTemplate>> surveyTemplateDataFetcher = environment -> {
        List<SurveyTemplate> surveyTemplates = new ArrayList<>();
        getSurveyTemplateRepository().findAll().forEach(surveyTemplates::add);
        return surveyTemplates;
    };

    public DataFetcher<List<Question>> questionsDataFetcher = environment -> {
        SurveyTemplate surveyTemplate = environment.getSource();
        return surveyTemplate.getQuestions();
    };

    public DataFetcher<List<Survey>> surveysAdministered = environment -> {
        SurveyTemplate surveyTemplate = environment.getSource();
        return surveyTemplate.getSurveysAdministered();
    };
}
