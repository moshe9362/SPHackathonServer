package hackathon.server.controllers;

import hackathon.server.dal.crud.ExcelDataRepository;
import hackathon.server.dal.crud.ExerciseRecordRepository;
import hackathon.server.dal.crud.ExerciseRepository;
import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.api.PatientExerciseRecordReply;
import hackathon.server.models.api.PatientExercisesExcelDataReply;
import hackathon.server.models.api.PatientReply;
import hackathon.server.models.api.enums.Gender;
import hackathon.server.models.db.ExcelData;
import hackathon.server.models.db.Exercise;
import hackathon.server.models.db.ExerciseRecord;
import hackathon.server.models.db.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DoctorController {

    private PatientRepository patientRepository;
    private ExerciseRepository exerciseRepository;
    private ExerciseRecordRepository exerciseRecordRepository;
    private ExcelDataRepository excelDataRepository;

    @Autowired
    public DoctorController(PatientRepository patientRepository,
                            ExerciseRepository exerciseRepository,
                            ExerciseRecordRepository exerciseRecordRepository,
                            ExcelDataRepository excelDataRepository) {
        this.patientRepository = patientRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseRecordRepository = exerciseRecordRepository;
        this.excelDataRepository = excelDataRepository;
    }

    @GetMapping("/patients")
    @ResponseBody
    public ArrayList<PatientReply> getAllPatients() {
        List<Patient> dbResult = patientRepository.findAll();
        ArrayList<PatientReply> reply = new ArrayList<>();
        for (Patient patient : dbResult) {
             reply.add(createPatientReply(patient));
        }
        return reply;
    }

    private PatientReply createPatientReply(Patient patient) {
        PatientReply patientReply = new PatientReply();
        patientReply.setUuid(patient.getUuid());
        patientReply.setIdNumber(patient.getIdNumber());
        patientReply.setFirstName(patient.getFirstName());
        patientReply.setLastName(patient.getLastName());
        patientReply.setBirthDate(patient.getBirthDate());
        patientReply.setGender(Gender.values()[patient.getGender()]);
        patientReply.setEmail(patient.getEmail());
        patientReply.setPhone(patient.getPhone());
        return patientReply;
    }

    @GetMapping("/exerciseRecordsForId/{uuid}")
    public ResponseEntity<List<PatientExerciseRecordReply>> getExerciseRecordsById(@PathVariable("uuid") Long uuid) {
        List<ExerciseRecord> recordsList = exerciseRecordRepository.findByPatientUuid(uuid.toString());
        List<PatientExerciseRecordReply> result = map(recordsList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private List<PatientExerciseRecordReply> map(List<ExerciseRecord> list) {
        List<PatientExerciseRecordReply> patientExerciseRecordReplies = new ArrayList<>();
        for (ExerciseRecord exerciseRecord : list) {
            PatientExerciseRecordReply patientExerciseRecordReply = new PatientExerciseRecordReply();
            Exercise exercise = exerciseRepository.findById(exerciseRecord.getExerciseId()).get();
            patientExerciseRecordReply.setExerciseId(exercise.getId());
            patientExerciseRecordReply.setExerciseName(exercise.getName());
            patientExerciseRecordReply.setExerciseTypeId(exercise.getExerciseType().getId());
            patientExerciseRecordReply.setExerciseTypeName(exercise.getExerciseType().getName());

            patientExerciseRecordReply.setEndDateTimeOfExercise(exerciseRecord.getEndOfExercise().toString());
            patientExerciseRecordReply.setExerciseData(exerciseRecord.getExerciseData());

            patientExerciseRecordReply.setId(exerciseRecord.getId());
        }

        return patientExerciseRecordReplies;
    }


    @GetMapping("/exercisePatientExcelData/{uuid}")
    @ResponseBody
    public ArrayList<PatientExercisesExcelDataReply> getAllPatientExercisesExcelData(@PathVariable("uuid") String patientUuid) {
        List<ExerciseRecord> patientExerciseRecords = exerciseRecordRepository.findByPatientUuid(patientUuid);
        ArrayList<PatientExercisesExcelDataReply> reply = new ArrayList<>();
        for (ExerciseRecord exerciseRecord: patientExerciseRecords) {
            reply.addAll(getExerciseExcelData(exerciseRecord));
        }
        return reply;
    }

    private ArrayList<PatientExercisesExcelDataReply> getExerciseExcelData (ExerciseRecord exerciseRecord) {
        ArrayList<PatientExercisesExcelDataReply> exerciseExcelDataReply = new ArrayList<>();
        List<ExcelData> excelDataRecords = excelDataRepository.findAllByExerciseRecordId(exerciseRecord.getId());
        for (ExcelData excelDataEntry : excelDataRecords) {
            exerciseExcelDataReply.add(createPatientExercisesExcelDataReply(excelDataEntry,exerciseRecord));
        }
        return exerciseExcelDataReply;
    }

    private PatientExercisesExcelDataReply createPatientExercisesExcelDataReply(ExcelData excelDataEntry, ExerciseRecord exerciseRecord) {
        PatientExercisesExcelDataReply patientExercisesExcelDataReply = new PatientExercisesExcelDataReply();
        Exercise exercise = exerciseRepository.findById(exerciseRecord.getExerciseId()).get();
        patientExercisesExcelDataReply.setExerciseRecordId(exerciseRecord.getId());
        patientExercisesExcelDataReply.setExerciseId(exerciseRecord.getExerciseId());
        patientExercisesExcelDataReply.setExerciseName(exercise.getName());
        patientExercisesExcelDataReply.setAngle(excelDataEntry.getAngle());
        patientExercisesExcelDataReply.setTimeStamp(new Date(excelDataEntry.getTimestamp().getTime()));
        patientExercisesExcelDataReply.setExerciseTypeId(exercise.getExerciseType().getId());
        patientExercisesExcelDataReply.setExerciseTypeName(exercise.getExerciseType().getName());

        return patientExercisesExcelDataReply;
    }

}