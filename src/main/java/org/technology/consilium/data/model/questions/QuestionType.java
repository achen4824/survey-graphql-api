package org.technology.consilium.data.model.questions;

import org.json.JSONObject;

import java.util.Map;
import java.util.function.Function;

public enum QuestionType {
    NPS,
    BINARY,
    COMMENT,
    NUMBER,
    MULTIPLE,
    RATING;

    public static final Map<QuestionType, Function<JSONObject, Question>> KEY_BUILDER = Map.of(
            NPS, NPSQuestion::fromJSONObject,
            BINARY, BinaryQuestion::fromJSONObject,
            COMMENT, CommentQuestion::fromJSONObject,
            NUMBER, NumberQuestion::fromJSONObject,
            MULTIPLE, MultipleAnswerQuestion::fromJSONObject,
            RATING, RatingQuestion::fromJSONObject
    );
}
