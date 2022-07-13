package org.technology.consilium.data.model.questions;

import lombok.Data;
import org.json.JSONObject;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
public class MultipleAnswerQuestion extends Question{

    @ElementCollection
    private List<String> values;

    @OneToMany(orphanRemoval = true, cascade= CascadeType.ALL)
    public List<Question> subQuestions;

    public MultipleAnswerQuestion() {
        values = new ArrayList<>();
        subQuestions = new ArrayList<>();
        questionType = QuestionType.MULTIPLE;
    }

    public static MultipleAnswerQuestion fromJSONObject(JSONObject object) {
        MultipleAnswerQuestion multipleAnswerQuestion = new MultipleAnswerQuestion();
        multipleAnswerQuestion.fromJSON(object);
        if(object.has("values")){
            for(int i = 0;i < object.getJSONArray("values").length(); i++) {
                multipleAnswerQuestion.values.add(object.getJSONArray("values").getString(i));
            }
        }

        if (object.has("subQuestions"))
            multipleAnswerQuestion.subQuestions = Question.getQuestionsFromJSONArray(object.getJSONArray("subQuestions"));
        return multipleAnswerQuestion;
    }

    @Override
    public void resetId() {
        super.resetId();
        subQuestions.forEach(question -> question.resetId());
    }

    @Override
    public List<Question> flatten(Question nextQuestion) {
        this.nextQuestion = nextQuestion.getQuestionData().getUniqueID();
        List<Question> questionList = new ArrayList<>();
        questionList.add(this);
        if(subQuestions.size() != 0) {
            this.nextQuestion = subQuestions.get(0).getQuestionData().getUniqueID();
            for (int i = 0; i < subQuestions.size() - 1; i++) {
                questionList.addAll(subQuestions.get(i).flatten(subQuestions.get(i + 1)));
            }
            questionList.addAll(subQuestions.get(subQuestions.size() - 1).flatten(nextQuestion));
        }
        return questionList;
    }

    @Override
    public Question clone() {
        MultipleAnswerQuestion multipleAnswerQuestionClone = new MultipleAnswerQuestion();
        multipleAnswerQuestionClone.copyValues(this);
        multipleAnswerQuestionClone.setValues(new ArrayList<>(values));
        multipleAnswerQuestionClone.setSubQuestions(subQuestions.stream().map(Question::clone).collect(Collectors.toList()));
        return multipleAnswerQuestionClone;
    }
}
