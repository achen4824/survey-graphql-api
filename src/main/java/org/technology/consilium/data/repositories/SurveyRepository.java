package org.technology.consilium.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.technology.consilium.data.model.Survey;

public interface SurveyRepository extends CrudRepository<Survey, Long> {
}
