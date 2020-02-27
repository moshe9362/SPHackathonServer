package hackathon.server.dal.crud;


import hackathon.server.models.db.Exercise;
import hackathon.server.models.db.ExerciseRecord;
import hackathon.server.models.db.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {

    List<Exercise> findAll();

    Optional<Exercise> findById(Long id);
}