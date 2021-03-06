package org.technology.consilium.data.model.questions;

import lombok.Data;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CommentQuestion extends Question{

    @Column(columnDefinition = "TEXT")
    private String comment;

    public CommentQuestion() {
        questionType = QuestionType.COMMENT;
    }

    public static CommentQuestion fromJSONObject(JSONObject object) {
        CommentQuestion commentQuestion = new CommentQuestion();
        commentQuestion.fromJSON(object);
        commentQuestion.comment = object.has("comment") ? object.getString("comment") : null;
        return commentQuestion;
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
        CommentQuestion commentQuestionClone = new CommentQuestion();
        commentQuestionClone.copyValues(this);
        commentQuestionClone.setComment(comment);
        return commentQuestionClone;
    }
}
