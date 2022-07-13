package org.technology.consilium.data.wiring;

import graphql.schema.DataFetcher;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.technology.consilium.data.model.questions.CommentQuestion;
import org.technology.consilium.data.model.questions.Question;
import org.technology.consilium.data.model.Survey;
import org.technology.consilium.data.model.SurveyTemplate;
import org.technology.consilium.data.model.Surveyee;
import org.technology.consilium.data.model.questions.QuestionData;
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
        List<Question> flattenedQuestionList = new ArrayList<>();
        List<Question> questionList = survey.getQuestions();

        for(int i = 0; i < questionList.size()-1; i++){
            flattenedQuestionList.addAll(questionList.get(i).flatten(questionList.get(i + 1)));
        }

        CommentQuestion terminatingQuestion = new CommentQuestion();
        terminatingQuestion.setQuestionData(new QuestionData());
        terminatingQuestion.getQuestionData().setUniqueID(null);
        flattenedQuestionList.addAll(questionList.get(questionList.size() - 1).flatten(terminatingQuestion));
        return flattenedQuestionList;
    };
}
