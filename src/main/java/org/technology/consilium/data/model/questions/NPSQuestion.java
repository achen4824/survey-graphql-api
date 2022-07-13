package org.technology.consilium.data.model.questions;

import lombok.Data;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class NPSQuestion extends Question{

    @Min(0)
    @Max(10)
    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String comment;

    public NPSQuestion() {
        questionType = QuestionType.NPS;
    }

    public static NPSQuestion fromJSONObject(JSONObject object) {
        NPSQuestion question = new NPSQuestion();
        question.fromJSON(object);
        question.score = object.has("score") ? object.getInt("score") : null;
        question.comment = object.has("comment") ? object.getString("comment") : null;

        return question;
    }

    public void setScoreStr(String score) {
        try{
            this.score = Integer.parseInt(score);
        }catch(NumberFormatException ignored) {

        }
    }

    @Override
    public List<Question> flatten(Question nextQuestion) {
        List<Question> questionList = new ArrayList<>();
        this.nextQuestion = nextQuestion.getQuestionData().getUniqueID();
        questionList.add(this);
        return questionList;
    }

    @Override
    public Question clone() {
        NPSQuestion npsQuestion = new NPSQuestion();
        npsQuestion.copyValues(this);
        npsQuestion.setComment(comment);
        npsQuestion.setScore(score);
        return npsQuestion;
    }
}
