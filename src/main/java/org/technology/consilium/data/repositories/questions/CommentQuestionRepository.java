package org.technology.consilium.data.repositories.questions;

import org.springframework.data.repository.CrudRepository;
import org.technology.consilium.data.model.questions.CommentQuestion;

public interface CommentQuestionRepository extends CrudRepository<CommentQuestion, Long> {
}
