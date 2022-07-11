package org.technology.consilium.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.technology.consilium.data.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
