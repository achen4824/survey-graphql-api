package org.technology.consilium.data.model.questions;

import lombok.Data;
import org.json.JSONObject;

import javax.persistence.Entity;

@Entity
@Data
public class NumberQuestion extends Question{

    private Integer value;

    public NumberQuestion() {
        questionType = QuestionType.NUMBER;
    }

    public static NumberQuestion fromJSONObject(JSONObject object) {
        NumberQuestion question = new NumberQuestion();
        question.fromJSON(object);
        question.value = object.has("value") ? object.getInt("value") : null;
        return question;
    }

    public void setValueStr(String score) {
        try{
            this.value = Integer.parseInt(score);
        }catch(NumberFormatException ignored) {

        }
    }

    @Override
    public Question clone() {
        NumberQuestion numberQuestionClone = new NumberQuestion();
        numberQuestionClone.copyValues(this);
        numberQuestionClone.setValue(value);
        return numberQuestionClone;
    }
}
