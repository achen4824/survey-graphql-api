package org.technology.consilium.data.repositories.questions;

import org.springframework.data.repository.CrudRepository;
import org.technology.consilium.data.model.questions.MultipleAnswerQuestion;

public interface MultipleAnswerQuestionRepository extends CrudRepository<MultipleAnswerQuestion, Long> {
}
