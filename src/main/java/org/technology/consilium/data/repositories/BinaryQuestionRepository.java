package org.technology.consilium.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.technology.consilium.data.model.BinaryQuestion;

public interface BinaryQuestionRepository extends CrudRepository<BinaryQuestion, Long> {
}
