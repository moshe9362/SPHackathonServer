package hackathon.server.dal;

import hackathon.server.Utils;
import hackathon.server.dal.crud.ExcelDataRepository;
import hackathon.server.dal.crud.ExerciseRecordRepository;
import hackathon.server.dal.crud.ExerciseRepository;
import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.api.ExcelDataRequest;
import hackathon.server.models.api.ExerciseRecordRequest;
import hackathon.server.models.db.ExcelData;
import hackathon.server.models.db.Exercise;
import hackathon.server.models.db.ExerciseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
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
        System.out.println("Saved record in DB");
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

            // the following piece of shit is to convert string to timestamp
            Timestamp excelDataTimeStamp = Utils.convertStringToTimeStamp(old.getTimeStamp());
            mappedExcelData.setTimestamp(excelDataTimeStamp);

            mappedExcelData.setExerciseRecordId(id);
            mappedExcelDataList.add(mappedExcelData);
        }
        return mappedExcelDataList;
    }

    private ExerciseRecord mapExerciseApiToDB(ExerciseRecordRequest exerciseRecordRequest) {
        Exercise exercise = exerciseRepository.findById(exerciseRecordRequest.getExerciseId()).get();

        ExerciseRecord mappedExerciseRecord = new ExerciseRecord();

        mappedExerciseRecord.setExercise(exercise);
        mappedExerciseRecord.setPatient(patientRepository.findByUuid(exerciseRecordRequest.getUserUuid()));

        mappedExerciseRecord.setStartOfExercise(Utils.convertStringToDate(exerciseRecordRequest.getStartDateOfExercise()));
        mappedExerciseRecord.setEndOfExercise(Utils.convertStringToDate(exerciseRecordRequest.getEndDateOfExercise()));
        mappedExerciseRecord.setExtraData(exerciseRecordRequest.getExerciseData());
        return mappedExerciseRecord;
    }

    public void insertExcelData(List<ExcelData> excelData) {
        for(ExcelData a : excelData) {
            excelDataRepository.save(a);
        }
        System.out.println("Saved ExcelData in DB");
    }
}