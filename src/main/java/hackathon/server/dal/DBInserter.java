package hackathon.server.dal;

import hackathon.server.utils.Utils;
import hackathon.server.dal.crud.ExcelDataRepository;
import hackathon.server.dal.crud.ExerciseRecordRepository;
import hackathon.server.dal.crud.ExerciseRepository;
import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.api.ExcelDataRequest;
import hackathon.server.models.api.ExerciseRecordRequest;
import hackathon.server.models.db.ExcelData;
import hackathon.server.models.db.ExerciseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBInserter {

    @Autowired
    private ExerciseRecordRepository exerciseRecordRepository;

    @Autowired
    private ExcelDataRepository excelDataRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private PatientRepository patientRepository;

    public void insertExerciseRecord(ExerciseRecordRequest exerciseRecord) {
        ExerciseRecord recordForDB = mapExerciseApiToDB(exerciseRecord);
        ExerciseRecord result = exerciseRecordRepository.save(recordForDB);
        long id = result.getId();
        List<ExcelDataRequest> excelDataList = exerciseRecord.getExcelData();
        List<ExcelData> mappedExcelDataList = mapExcelDataApiToDB(excelDataList, id);
        insertExcelData(mappedExcelDataList);
    }

    private List<ExcelData> mapExcelDataApiToDB(List<ExcelDataRequest> excelDataList, long id) {
        List<ExcelData> mappedExcelDataList = new ArrayList<>();
        for (ExcelDataRequest old : excelDataList) {
            ExcelData mappedExcelData = new ExcelData();
            mappedExcelData.setAngle(old.getAngle());
            mappedExcelData.setExerciseRecordId(id);

            Instant instant = Instant.parse(old.getTimeStamp());
            Date date = new Date(instant.toEpochMilli());
            mappedExcelData.setTimestamp(date);

            mappedExcelDataList.add(mappedExcelData);
        }
        return mappedExcelDataList;
    }

    private ExerciseRecord mapExerciseApiToDB(ExerciseRecordRequest exerciseRecordRequest) {

        ExerciseRecord mappedExerciseRecord = new ExerciseRecord();
        mappedExerciseRecord.setExerciseId(exerciseRecordRequest.getExerciseId());
        mappedExerciseRecord.setPatientUuid(exerciseRecordRequest.getUserUuid());
        Instant startInstant = Instant.parse(exerciseRecordRequest.getStartDateOfExercise());
        Date startDate = new Date(startInstant.toEpochMilli());
        mappedExerciseRecord.setStartOfExercise(startDate);
        Instant endInstant = Instant.parse(exerciseRecordRequest.getStartDateOfExercise());
        Date endDate = new Date(endInstant.toEpochMilli());
        mappedExerciseRecord.setEndOfExercise(endDate);
        mappedExerciseRecord.setExerciseData(exerciseRecordRequest.getExerciseData());
        return mappedExerciseRecord;
    }

    public void insertExcelData(List<ExcelData> excelData) {
        for(ExcelData excelData1 : excelData) {
            excelDataRepository.save(excelData1);
        }
    }
}