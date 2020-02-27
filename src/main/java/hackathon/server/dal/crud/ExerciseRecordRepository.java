package hackathon.server.dal.crud;


import hackathon.server.models.db.Exercise;
import hackathon.server.models.db.ExerciseRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExerciseRecordRepository extends CrudRepository<ExerciseRecord, Long> {

    List<ExerciseRecord> findAll();

    List<ExerciseRecord> findByPatientUuid(String uuid);

}
