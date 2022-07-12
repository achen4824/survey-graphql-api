package org.technology.consilium.data.model.questions;

import lombok.Data;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class BinaryQuestion extends Question {

    private Boolean isYes;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<Question> yesQuestion;


    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<Question> noQuestion;

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
    public Question clone() {
        BinaryQuestion binaryQuestionClone = new BinaryQuestion();
        binaryQuestionClone.copyValues(this);
        binaryQuestionClone.setIsYes(isYes);
        binaryQuestionClone.setYesQuestion(this.yesQuestion.stream().map(Question::clone).collect(Collectors.toList()));
        binaryQuestionClone.setNoQuestion(this.noQuestion.stream().map(Question::clone).collect(Collectors.toList()));
        return binaryQuestionClone;
    }
}
