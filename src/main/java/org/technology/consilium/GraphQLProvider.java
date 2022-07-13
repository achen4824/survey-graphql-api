package org.technology.consilium;

import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.com.google.common.base.Charsets;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.technology.consilium.data.model.questions.Question;
import org.technology.consilium.data.model.questions.QuestionData;
import org.technology.consilium.data.wiring.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    private final QuestionWiring questionWiring;

    private final SurveyeeWiring surveyeeWiring;

    private final SurveyorWiring surveyorWiring;

    private final SurveyTemplateWiring surveyTemplateWiring;

    private final SurveyWiring surveyWiring;

    @Autowired
    public GraphQLProvider(QuestionWiring questionWiring,
                           SurveyeeWiring surveyeeWiring,
                           SurveyorWiring surveyorWiring,
                           SurveyTemplateWiring surveyTemplateWiring,
                           SurveyWiring surveyWiring) {
        this.questionWiring = questionWiring;
        this.surveyeeWiring = surveyeeWiring;
        this.surveyorWiring = surveyorWiring;
        this.surveyTemplateWiring = surveyTemplateWiring;

        this.surveyWiring = surveyWiring;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("survey_schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);

        Instrumentation instrumentation = new TracingInstrumentation();

        this.graphQL = GraphQL.newGraphQL(graphQLSchema).instrumentation(instrumentation).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("surveyees", surveyeeWiring.surveyeeDataFetcher)
                        .dataFetcher("surveyors", surveyorWiring.surveyorDataFetcher)
                        .dataFetcher("surveys", surveyWiring.surveyDataFetcher)
                        .dataFetcher("questionDatas", questionWiring.questionFetcher(QuestionData.class))
                        .dataFetcher("surveyTemplates", surveyTemplateWiring.surveyTemplateDataFetcher)
                )
                .type(newTypeWiring("Surveyee")
                        .dataFetcher("surveys", surveyeeWiring.surveysDataFetcher)
                )
                .type(newTypeWiring("Surveyor")
                        .dataFetcher("surveysAdministered", surveyorWiring.surveyorDataFetcher)
                )
                .type(newTypeWiring("Survey")
                        .dataFetcher("surveyee", surveyWiring.surveyeeDataFetcher)
                        .dataFetcher("survey", surveyWiring.surveyTemplateDataFetcher)
                        .dataFetcher("questions", surveyWiring.questionsDataFetcher)
                )
                .type(newTypeWiring("Question")
                        .typeResolver(questionWiring.questionTypeResolver)
                )
                .type(newTypeWiring("MultipleAnswerQuestion")
                        .dataFetcher("questionData", questionWiring.questionDataDataFetcher)
                        .dataFetcher("subQuestions", questionWiring.subQuestionDataFetcher)
                )
                .type(newTypeWiring("CommentQuestion")
                        .dataFetcher("questionData", questionWiring.questionDataDataFetcher)
                )
                .type(newTypeWiring("NumberQuestion")
                        .dataFetcher("questionData", questionWiring.questionDataDataFetcher)
                )
                .type(newTypeWiring("NPSQuestion")
                        .dataFetcher("questionData", questionWiring.questionDataDataFetcher)
                )
                .type(newTypeWiring("BinaryQuestion")
                        .dataFetcher("questionData", questionWiring.questionDataDataFetcher)
                        .dataFetcher("yesQuestion", questionWiring.yesQuestionDataFetcher)
                        .dataFetcher("noQuestion", questionWiring.noQuestionDataFetcher)
                )
                .type(newTypeWiring("RatingQuestion")
                        .dataFetcher("questionData", questionWiring.questionDataDataFetcher)
                        .dataFetcher("goodQuestion", questionWiring.goodQuestionDataFetcher)
                        .dataFetcher("neutralQuestion", questionWiring.neutralQuestionDataFetcher)
                        .dataFetcher("improvementQuestion", questionWiring.improvementQuestionDataFetcher)
                )
                .type(newTypeWiring("SurveyTemplate")
                        .dataFetcher("questions", surveyTemplateWiring.questionsDataFetcher)
                        .dataFetcher("surveysAdministered", surveyTemplateWiring.surveysAdministered)
                )
                .type(newTypeWiring("Gender")
                        .enumValues(surveyeeWiring.genderResolver)
                )
                .type(newTypeWiring("State")
                        .enumValues(surveyeeWiring.stateResolver)
                )
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}