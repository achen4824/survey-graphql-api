package org.technology.consilium.data.repositories.questions;

import org.springframework.data.repository.CrudRepository;
import org.technology.consilium.data.model.questions.NumberQuestion;

public interface NumberQuestionRepository extends CrudRepository<NumberQuestion, Long> {
}
