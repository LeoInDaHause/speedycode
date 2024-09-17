package io.leoindahause.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.leoindahause.model.Exercise;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
}