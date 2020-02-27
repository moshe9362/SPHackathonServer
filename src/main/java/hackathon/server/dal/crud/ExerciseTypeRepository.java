package hackathon.server.dal.crud;


import hackathon.server.models.db.ExerciseType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExerciseTypeRepository extends CrudRepository<ExerciseType, Long> {

    List<ExerciseType> findAll();
}
