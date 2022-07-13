package org.technology.consilium.data.model.questions;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Transient
    protected UUID nextQuestion;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    protected QuestionData questionData;

    protected QuestionType questionType;

    public void fromJSON(JSONObject object) {
        try {
            // In future add check to see if the question already exists in the database
            questionData = new QuestionData(object.getString("queryString"), object.getString("category"));
        }catch (JSONException e){
            log.error("All questions must contain both 'queryString', 'category', and 'questionType' keys");
        }

    }

    public static List<Question> getQuestionsFromJSONArray(JSONArray array) {
        if(Objects.nonNull(array)) {
            List<Question> questionList = new ArrayList<>();
            for(int i = 0; i < array.length(); i++){
                Question question =
                        QuestionType.KEY_BUILDER.get(QuestionType.valueOf(array.getJSONObject(i).getString("questionType")))
                            .apply(array.getJSONObject(i));
                questionList.add(question);
            }
            return questionList;
        }else{
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Question> T cast(Class<T> tClass){
        try {
            return (T) this;
        }catch (ClassCastException e){
            log.error("Attempted to cast to incorrect class wanted: " + tClass.getSimpleName() + "\tactually: " + this.getClass().getSimpleName());
            System.exit(1);
        }
        return null;
    }

    public void resetId(){
        id = null;
    }

    protected void copyValues(Question question) {
        this.questionData = question.questionData;  // Don't clone questionData so that there is no duplicate information
        this.questionType = question.questionType;
    }

    public abstract List<Question> flatten(Question nextQuestion);

    public abstract Question clone();

}
