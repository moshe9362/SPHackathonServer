package hackathon.server.dal.crud;


import hackathon.server.models.db.Exercise;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {

    List<Exercise> findAll();
    List<Exercise> findByProtocolId(Long protocolId);
    Optional<Exercise> findById(Long id);
}
