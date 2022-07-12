package org.technology.consilium.data.wiring;


import graphql.schema.DataFetcher;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.technology.consilium.data.model.questions.*;
import org.technology.consilium.data.repositories.questions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class QuestionWiring {

    @Getter
    public final BinaryQuestionRepository binaryQuestionRepository;

    @Getter
    public final NPSQuestionRepository npsQuestionRepository;

    @Getter
    public final MultipleAnswerQuestionRepository multipleAnswerQuestionRepository;

    @Getter
    public final NumberQuestionRepository numberQuestionRepository;

    @Getter
    public final RatingQuestionRepository ratingQuestionRepository;


    private Map<Class, CrudRepository> repos;

    @Autowired
    public QuestionWiring(BinaryQuestionRepository binaryQuestionRepository,
                          NPSQuestionRepository npsQuestionRepository,
                          MultipleAnswerQuestionRepository multipleAnswerQuestionRepository,
                          NumberQuestionRepository numberQuestionRepository,
                          RatingQuestionRepository ratingQuestionRepository) {

        this.binaryQuestionRepository = binaryQuestionRepository;
        this.npsQuestionRepository = npsQuestionRepository;
        this.multipleAnswerQuestionRepository = multipleAnswerQuestionRepository;
        this.numberQuestionRepository = numberQuestionRepository;
        this.ratingQuestionRepository = ratingQuestionRepository;

        repos = Map.of(
                BinaryQuestion.class, binaryQuestionRepository,
                NPSQuestion.class, npsQuestionRepository,
                MultipleAnswerQuestion.class, multipleAnswerQuestionRepository,
                NumberQuestion.class, numberQuestionRepository,
                RatingQuestion.class, ratingQuestionRepository
        );
    }

    @SuppressWarnings("unchecked")
    public <T> DataFetcher<List<T>> questionFetcher(Class<T> tClass) {
        return environment -> {
            List<T> questions = new ArrayList<>();
            repos.get(tClass).findAll().forEach(question -> questions.add((T) question));
            return questions;
        };
    }


    // Binary Question fields
    public DataFetcher<List<Question>> yesQuestionDataFetcher = environment -> {
        BinaryQuestion question = environment.getSource();
        return question.getYesQuestion();
    };

    public DataFetcher<List<Question>> noQuestionDataFetcher = environment -> {
        BinaryQuestion question = environment.getSource();
        return question.getNoQuestion();
    };


    // Rating Question
    public DataFetcher<Question> goodQuestionDataFetcher = environment -> {
        RatingQuestion question =  environment.getSource();
        return question.getGoodQuestion();
    };

    public DataFetcher<Question> neutralQuestionDataFetcher = environment -> {
        RatingQuestion question =  environment.getSource();
        return question.getNeutralQuestion();
    };

    public DataFetcher<Question> improvementQuestionDataFetcher = environment -> {
        RatingQuestion question =  environment.getSource();
        return question.getImprovementQuestion();
    };

    // Multiple Answer Question
    public DataFetcher<List<Question>> subQuestionDataFetcher = environment -> {
        MultipleAnswerQuestion question = environment.getSource();
        return question.getSubQuestions();
    };
}
