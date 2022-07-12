package org.technology.consilium.data.repositories.questions;

import org.springframework.data.repository.CrudRepository;
import org.technology.consilium.data.model.questions.RatingQuestion;

public interface RatingQuestionRepository extends CrudRepository<RatingQuestion, Long> {
}
