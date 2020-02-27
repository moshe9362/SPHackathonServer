package hackathon.server.dal.crud;


import hackathon.server.models.db.ExcelData;
import hackathon.server.models.db.ExerciseRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExcelDataRepository extends CrudRepository<ExcelData, Long> {

    List<ExcelData> findAll();
    List<ExcelData> findAllByExerciseRecordId(long exerciseRecordId);
}
