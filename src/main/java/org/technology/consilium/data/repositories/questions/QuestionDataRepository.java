package org.technology.consilium.data.repositories.questions;

import org.springframework.data.repository.CrudRepository;
import org.technology.consilium.data.model.questions.QuestionData;

public interface QuestionDataRepository extends CrudRepository<QuestionData, Long> {
}
