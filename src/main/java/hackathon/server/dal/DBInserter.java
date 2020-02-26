package hackathon.server.dal;

import hackathon.server.dal.crud.ExerciseRecordRepository;
import hackathon.server.models.api.ExcelDataRequest;
import hackathon.server.models.api.ExerciseRecordRequest;
import hackathon.server.models.db.ExerciseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Service
public class DBInserter {

    @Autowired
    private ExerciseRecordRepository exerciseRecordRepository;

    public void insertExerciseRecord(ExerciseRecordRequest exerciseRecord) {
        ExerciseRecord recordForDB = Converter.convert(exerciseRecord);
        ExerciseRecord result = exerciseRecordRepository.save(recordForDB);
        System.out.println("Saved record to DB");
        long id = result.getId();

        ExcelDataRequest excelData = new ExcelDataRequest();
        excelData.setAngle(1);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        excelData.setTimeStamp(timestamp.toString());
    }

    public void insertExcelData(List<ExcelDataRequest> excelData) {

        System.out.println("Saved ExcelData in DB");
    }
}