package org.technology.consilium.data.wiring;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.technology.consilium.data.model.Survey;
import org.technology.consilium.data.model.SurveyTemplate;
import org.technology.consilium.data.model.questions.CommentQuestion;
import org.technology.consilium.data.model.questions.Question;
import org.technology.consilium.data.model.questions.QuestionData;
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
        List<Question> flattenedQuestionList = new ArrayList<>();
        List<Question> questionList = surveyTemplate.getQuestions();

        for(int i = 0; i < questionList.size()-1; i++){
            flattenedQuestionList.addAll(questionList.get(i).flatten(questionList.get(i + 1)));
        }

        CommentQuestion terminatingQuestion = new CommentQuestion();
        terminatingQuestion.setQuestionData(new QuestionData());
        terminatingQuestion.getQuestionData().setUniqueID(null);
        flattenedQuestionList.addAll(questionList.get(questionList.size() - 1).flatten(terminatingQuestion));
        return flattenedQuestionList;
    };

    public DataFetcher<List<Survey>> surveysAdministered = environment -> {
        SurveyTemplate surveyTemplate = environment.getSource();
        return surveyTemplate.getSurveysAdministered();
    };
}
