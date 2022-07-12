package org.technology.consilium;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import graphql.com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.technology.consilium.data.model.*;
import org.technology.consilium.data.model.questions.*;
import org.technology.consilium.data.repositories.SurveyRepository;
import org.technology.consilium.data.repositories.SurveyTemplateRepository;
import org.technology.consilium.data.repositories.SurveyeeRepository;
import org.technology.consilium.data.repositories.SurveyorRepository;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

//    @Autowired
//    private Validator validator;

    @Autowired
    SurveyorRepository surveyorRepository;

    @Autowired
    SurveyeeRepository surveyeeRepository;

    @Autowired
    SurveyTemplateRepository surveyTemplateRepository;

    @Autowired
    SurveyRepository surveyRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        List<SurveyTemplate> surveyTemplates = getSurveys();
        surveyTemplateRepository.saveAll(surveyTemplates);
        loadSurveyResponses(surveyTemplates.get(0));
    }

    public List<SurveyTemplate> getSurveys() throws IOException {
        List<SurveyTemplate> surveyTemplates = new ArrayList<>();
        URL url = Resources.getResource("survey.json");
        String json = Resources.toString(url, Charsets.UTF_8);
        JSONArray array = new JSONArray(json);

        for(int i = 0; i < array.length(); i++) {
            SurveyTemplate surveyTemplate = new SurveyTemplate();
            List<Question> questions =  Question.getQuestionsFromJSONArray(array.getJSONObject(i).getJSONArray("questions"));
            surveyTemplate.setQuestions(questions);
            surveyTemplates.add(surveyTemplate);
        }

        return surveyTemplates;
    }

    public void loadSurveyResponses(SurveyTemplate surveyTemplate)  {
        String filePath = "C:\\Users\\Andrew Chen\\IdeaProjects\\SurveyGraphql\\src\\main\\resources\\example_project_responses.csv";
        try{
            log.info("Reading from file: " + filePath);
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(8).build();

            List<String[]> fileContents = csvReader.readAll();


            Map<String, Surveyor> surveyorMap = new HashMap<>();
            Map<String, Surveyee> surveyeeMap = new HashMap<>();
            fileContents.forEach(arr -> {

                // Create all surveyors
                String surveyorName = arr[1];
                if(!surveyorMap.containsKey(surveyorName)) {
                    Surveyor surveyor = new Surveyor();
                    surveyor.setName(surveyorName);
                    surveyorMap.put(surveyorName, surveyorRepository.save(surveyor));
                }

                // Create all surveyees
                String surveyeeName = !arr[3].equals("CONFIDENTIAL") ? arr[3] : arr[0];
                if(!surveyeeMap.containsKey(surveyeeName)) {
                    Surveyee surveyee = new Surveyee();
                    surveyee.setName(surveyeeName);
                    surveyee.setCompany(arr[2]);
                    surveyee.setEmail(arr[5]);
                    surveyee.setMobile(arr[6]);
                    surveyee.setOfficePhone(arr[7]);
                    try {
                        surveyee.setState(State.valueOf(arr[8]));
                    }catch(IllegalArgumentException ignored){

                    }
                    surveyee.setContactName(arr[12]);
                    Gender gender = (arr[47].equals("M")) ? Gender.MALE : Gender.FEMALE;
                    surveyee.setGender(gender);
                    surveyee.setAgeRange(arr[48]);
                    surveyeeMap.put(surveyeeName, surveyeeRepository.save(surveyee));
                }
            });

            // Populate Questions
            fileContents.forEach(arr -> {
                // Clone questions
                List<Question> questions = surveyTemplate.getQuestions().stream().map(Question::clone).collect(Collectors.toList());
                questions.forEach(Question::resetId);

                NPSQuestion question1 = questions.get(0).cast(NPSQuestion.class);
                question1.setScoreStr(arr[14]);
                question1.setComment(arr[15]);

                NumberQuestion question2 = questions.get(1).cast(NumberQuestion.class);
                question2.setValueStr(arr[16]);

                BinaryQuestion question3 = questions.get(2).cast(BinaryQuestion.class);
                question3.setYes(arr[17]);
                if(question3.getIsYes()){
                    NPSQuestion question3a = question3.getYesQuestion().get(0).cast(NPSQuestion.class);
                    question3a.setScoreStr(arr[18]);
                    question3a.setComment(arr[19]);
                }

                BinaryQuestion question4 = questions.get(3).cast(BinaryQuestion.class);
                question4.setYes(arr[20]);
                if(question4.getIsYes()){
                    NPSQuestion question4a = question4.getYesQuestion().get(0).cast(NPSQuestion.class);
                    question4a.setScoreStr(arr[21]);
                    question4a.setComment(arr[22]);
                }

                NumberQuestion question5 = questions.get(4).cast(NumberQuestion.class);
                question5.setValueStr(arr[23]);

                BinaryQuestion question6 = questions.get(5).cast(BinaryQuestion.class);
                question6.setYes(arr[24]);
                if(question6.getIsYes()){
                    CommentQuestion question6a = question6.getYesQuestion().get(0).cast(CommentQuestion.class);
                    question6a.setComment(arr[25]);
                }else{
                    CommentQuestion question6b = question6.getNoQuestion().get(0).cast(CommentQuestion.class);
                    question6b.setComment(arr[26]);
                }

                MultipleAnswerQuestion question7 = questions.get(6).cast(MultipleAnswerQuestion.class);
                MultipleAnswerQuestion question7a = question7.getSubQuestions().get(0).cast(MultipleAnswerQuestion.class);
                MultipleAnswerQuestion question7b = question7.getSubQuestions().get(1).cast(MultipleAnswerQuestion.class);
                List<String> values7 = new ArrayList<>();
                List<String> values7a = new ArrayList<>();
                if(!arr[30].equals("NA")) {
                    values7.add(arr[27]);
                    values7a.add(arr[30]);
                }
                if(!arr[31].equals("NA")) {
                    values7.add(arr[28]);
                    values7a.add(arr[31]);
                }
                if(!arr[32].equals("NA")) {
                    values7.add(arr[29]);
                    values7a.add(arr[32]);
                }
                question7.setValues(values7);
                question7a.setValues(values7a);
                List<String> values7b = new ArrayList<>();
                if(!arr[33].equals("NA"))
                    values7b.add(arr[33]);
                if(!arr[34].equals("NA"))
                    values7b.add(arr[34]);
                if(!arr[35].equals("NA"))
                    values7b.add(arr[35]);
                if(!arr[36].equals("NA"))
                    values7b.add(arr[36]);
                question7b.setValues(values7b);

                CommentQuestion question8 = questions.get(7).cast(CommentQuestion.class);
                question8.setComment(arr[37]);

                CommentQuestion question9 = questions.get(8).cast(CommentQuestion.class);
                question9.setComment(arr[38]);

                BinaryQuestion question10 = questions.get(9).cast(BinaryQuestion.class);
                question10.setIsYes(arr[39].equals("Growing"));
                if(question10.getIsYes()){
                    CommentQuestion question10a = question10.getYesQuestion().get(0).cast(CommentQuestion.class);
                    if(!arr[40].equals("NA"))
                        question10a.setComment(arr[40]);

                    BinaryQuestion question10b = question10.getYesQuestion().get(1).cast(BinaryQuestion.class);
                    question10b.setYes(arr[41]);
                    if(question10b.getIsYes()){
                        CommentQuestion question10ba = question10b.getYesQuestion().get(0).cast(CommentQuestion.class);
                        question10ba.setComment(arr[42]);
                    }
                }

                RatingQuestion question11 = questions.get(10).cast(RatingQuestion.class);
                question11.setScoreStr(arr[43]);
                if(Objects.nonNull(question11.getScore())) {
                    if(question11.getScore() == 10){
                        question11.getGoodQuestion().cast(CommentQuestion.class).setComment(arr[44]);
                    }else if(question11.getScore() == 9){
                        question11.getNeutralQuestion().cast(CommentQuestion.class).setComment(arr[44]);
                    }else{
                        question11.getImprovementQuestion().cast(CommentQuestion.class).setComment(arr[44]);
                    }
                }

                RatingQuestion question12 = questions.get(11).cast(RatingQuestion.class);
                question12.setScoreStr(arr[45]);
                if(Objects.nonNull(question12.getScore())) {
                    if(question12.getScore() < 9) {
                        question12.getImprovementQuestion().cast(CommentQuestion.class).setComment(arr[46]);
                    }
                }

                Survey survey = new Survey();
                survey.setSurvey(surveyTemplate);
                survey.setQuestions(questions);
                String surveyeeName = !arr[3].equals("CONFIDENTIAL") ? arr[3] : arr[0];
                survey.setSurveyee(surveyeeMap.get(surveyeeName));
                survey.setSurveyor(surveyorMap.get(arr[1]));
                survey.setDate(arr[49]);

                surveyRepository.save(survey);
            });



        }catch(IOException e){
            e.printStackTrace();
            log.error("File not found/unable to access at: " + filePath + " continuing...");
        }

    }
}
