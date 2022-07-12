package org.technology.consilium.data.repositories.questions;

import org.springframework.data.repository.CrudRepository;
import org.technology.consilium.data.model.questions.BinaryQuestion;

public interface BinaryQuestionRepository extends CrudRepository<BinaryQuestion, Long> {
}
