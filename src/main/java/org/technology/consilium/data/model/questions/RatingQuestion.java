package org.technology.consilium.data.model.questions;

import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Data
public class RatingQuestion extends Question{

    @Min(0)
    @Max(10)
    private Integer score;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "good_question_id", referencedColumnName = "id")
    private Question goodQuestion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "neutral_question_id", referencedColumnName = "id")
    private Question neutralQuestion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "improvement_question_id", referencedColumnName = "id")
    private Question improvementQuestion;

    public RatingQuestion() {
        questionType = QuestionType.RATING;
    }

    public static RatingQuestion fromJSONObject(JSONObject object) {
        RatingQuestion question = new RatingQuestion();
        question.fromJSON(object);

        question.goodQuestion = keyToQuestion(object, "goodQuestion");
        question.neutralQuestion = keyToQuestion(object, "neutralQuestion");
        question.improvementQuestion = keyToQuestion(object, "improvementQuestion");

        return question;
    }
    public static Question keyToQuestion(JSONObject object, String key){
        try {
            return QuestionType.KEY_BUILDER.get(
                            QuestionType.valueOf(object.getJSONObject(key).getString("questionType")))
                    .apply(object.getJSONObject(key));
        }catch(JSONException ignored){
            return null;
        }
    }

    @Override
    public void resetId() {
        super.resetId();
        if(Objects.nonNull(goodQuestion))
            goodQuestion.resetId();
        if(Objects.nonNull(neutralQuestion))
            neutralQuestion.resetId();
        if(Objects.nonNull(improvementQuestion))
            improvementQuestion.resetId();
    }

    @Override
    public Question clone() {
        RatingQuestion ratingQuestionClone = new RatingQuestion();
        ratingQuestionClone.copyValues(this);
        ratingQuestionClone.setScore(score);
        ratingQuestionClone.goodQuestion = Objects.nonNull(goodQuestion) ? goodQuestion.clone() : null;
        ratingQuestionClone.neutralQuestion = Objects.nonNull(neutralQuestion) ? neutralQuestion.clone() : null;
        ratingQuestionClone.improvementQuestion = Objects.nonNull(improvementQuestion) ? improvementQuestion.clone() : null;
        return ratingQuestionClone;
    }

    public void setScoreStr(String score) {
        try{
            this.score = Integer.parseInt(score);
        }catch(NumberFormatException ignored) {

        }
    }
}
