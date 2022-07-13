package org.technology.consilium.data.model.questions;

import lombok.Data;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
public class BinaryQuestion extends Question {

    private Boolean isYes;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<Question> yesQuestion;

    @Transient
    private UUID yesNextQuestion;


    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<Question> noQuestion;

    @Transient
    private UUID noNextQuestion;

    public BinaryQuestion() {
        super();
        yesQuestion = new ArrayList<>();
        noQuestion = new ArrayList<>();
        questionType = QuestionType.BINARY;
    }

    public static BinaryQuestion fromJSONObject(JSONObject object) {
        BinaryQuestion binaryQuestion = new BinaryQuestion();
        binaryQuestion.fromJSON(object);
        binaryQuestion.isYes = object.has("isYes") ? object.getString("isYes").equals("true") : null;

        if(object.has("yesQuestion"))
            binaryQuestion.yesQuestion = Question.getQuestionsFromJSONArray(object.getJSONArray("yesQuestion"));
        if(object.has("noQuestion"))
            binaryQuestion.noQuestion = Question.getQuestionsFromJSONArray(object.getJSONArray("noQuestion"));

        return binaryQuestion;
    }

    public void setYes(String isYes) {
        this.isYes = isYes.equals("Yes");
    }

    @Override
    public void resetId() {
        super.resetId();
        yesQuestion.forEach(Question::resetId);
        noQuestion.forEach(Question::resetId);
    }

    @Override
    public List<Question> flatten(Question nextQuestion) {
        this.nextQuestion = nextQuestion.getQuestionData().getUniqueID();
        List<Question> questionList = new ArrayList<>();
        questionList.add(this);
        if(yesQuestion.size() == 0){
            this.yesNextQuestion = this.nextQuestion;
        }else {
            this.yesNextQuestion = yesQuestion.get(0).getQuestionData().getUniqueID();
            for (int i = 0; i < yesQuestion.size() - 1; i++) {
                questionList.addAll(yesQuestion.get(i).flatten(yesQuestion.get(i + 1)));
            }
            questionList.addAll(yesQuestion.get(yesQuestion.size() - 1).flatten(nextQuestion));
        }

        if(noQuestion.size() == 0){
            this.noNextQuestion = this.nextQuestion;
        }else {
            this.noNextQuestion = noQuestion.get(0).getQuestionData().getUniqueID();
            for (int i = 0; i < noQuestion.size() - 1; i++) {
                questionList.addAll(noQuestion.get(i).flatten(noQuestion.get(i + 1)));
            }
            questionList.addAll(noQuestion.get(noQuestion.size() - 1).flatten(nextQuestion));
        }
        return questionList;
    }

    @Override
    public Question clone() {
        BinaryQuestion binaryQuestionClone = new BinaryQuestion();
        binaryQuestionClone.copyValues(this);
        binaryQuestionClone.setIsYes(isYes);
        binaryQuestionClone.setYesQuestion(this.yesQuestion.stream().map(Question::clone).collect(Collectors.toList()));
        binaryQuestionClone.setNoQuestion(this.noQuestion.stream().map(Question::clone).collect(Collectors.toList()));
        return binaryQuestionClone;
    }
}
