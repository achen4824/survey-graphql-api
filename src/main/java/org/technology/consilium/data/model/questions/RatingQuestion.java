package org.technology.consilium.data.model.questions;

import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    @Transient
    private UUID goodNextQuestion;
    @Transient
    private UUID neutralNextQuestion;
    @Transient
    private UUID improvementNextQuestion;

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
    public List<Question> flatten(Question nextQuestion) {
        List<Question> questionList = new ArrayList<>();
        this.nextQuestion = nextQuestion.getQuestionData().getUniqueID();
        questionList.add(this);

        goodNextQuestion = verifyNextQuestion(goodQuestion, questionList, nextQuestion);
        neutralNextQuestion = verifyNextQuestion(neutralQuestion, questionList, nextQuestion);
        improvementNextQuestion = verifyNextQuestion(improvementQuestion, questionList, nextQuestion);

        return questionList;
    }

    private UUID verifyNextQuestion(Question fieldQuestion, List<Question> questionList, Question nextQuestion){
        if (Objects.isNull(fieldQuestion)) {
            return this.nextQuestion;
        }else {
            questionList.addAll(fieldQuestion.flatten(nextQuestion));
            return fieldQuestion.getQuestionData().getUniqueID();
        }

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
