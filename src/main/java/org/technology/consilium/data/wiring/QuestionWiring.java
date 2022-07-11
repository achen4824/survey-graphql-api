package org.technology.consilium.data.wiring;


import graphql.schema.DataFetcher;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.technology.consilium.data.model.*;
import org.technology.consilium.data.repositories.BinaryQuestionRepository;
import org.technology.consilium.data.repositories.BinaryQuestionTemplateRepository;
import org.technology.consilium.data.repositories.NPSQuestionRepository;
import org.technology.consilium.data.repositories.NPSQuestionTemplateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class QuestionWiring {

    @Getter
    public final BinaryQuestionRepository binaryQuestionRepository;

    @Getter
    public final BinaryQuestionTemplateRepository binaryQuestionTemplateRepository;

    @Getter
    public final NPSQuestionRepository npsQuestionRepository;

    @Getter
    public final NPSQuestionTemplateRepository npsQuestionTemplateRepository;

    private Map<Class, CrudRepository> repos;

    @Autowired
    public QuestionWiring(BinaryQuestionRepository binaryQuestionRepository,
                          BinaryQuestionTemplateRepository binaryQuestionTemplateRepository,
                          NPSQuestionRepository npsQuestionRepository,
                          NPSQuestionTemplateRepository npsQuestionTemplateRepository) {

        this.binaryQuestionRepository = binaryQuestionRepository;
        this.binaryQuestionTemplateRepository = binaryQuestionTemplateRepository;
        this.npsQuestionRepository = npsQuestionRepository;
        this.npsQuestionTemplateRepository = npsQuestionTemplateRepository;

        repos = Map.of(
                    BinaryQuestion.class, binaryQuestionRepository,
                    BinaryQuestionTemplate.class, binaryQuestionTemplateRepository,
                    NPSQuestion.class, npsQuestionRepository,
                    NPSQuestionTemplate.class, npsQuestionTemplateRepository
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

    public DataFetcher<NPSQuestionTemplate> templateDataFetcher = environment -> {
        NPSQuestion question = environment.getSource();
        return question.getQuery_();
    };

    public DataFetcher<BinaryQuestionTemplate> binaryTemplateDataFetcher = environment -> {
        BinaryQuestion question = environment.getSource();
        return question.getQuery_();
    };

    public DataFetcher<NPSQuestion> yesQuestionDataFetcher = environment -> {
        BinaryQuestion question = environment.getSource();
        return question.getYesQuestion();
    };

    public DataFetcher<NPSQuestion> noQuestionDataFetcher = environment -> {
        BinaryQuestion question = environment.getSource();
        return question.getNoQuestion();
    };
}
