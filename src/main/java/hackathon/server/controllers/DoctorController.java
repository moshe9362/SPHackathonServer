package hackathon.server.controllers;

import hackathon.server.dal.crud.ExcelDataRepository;
import hackathon.server.dal.crud.ExerciseRecordRepository;
import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.api.PatientExercisesExcelDataReply;
import hackathon.server.models.api.PatientReply;
import hackathon.server.models.api.enums.Gender;
import hackathon.server.models.db.ExcelData;
import hackathon.server.models.db.ExerciseRecord;
import hackathon.server.models.db.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class DoctorController {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ExerciseRecordRepository exerciseRecordRepository;

    @Autowired
    ExcelDataRepository excelDataRepository;

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

    @GetMapping("/exercisePatientExcelData/{uuid}")
    @ResponseBody
    public ArrayList<PatientExercisesExcelDataReply> getAllPatientExercisesExcelData(@PathVariable("uuid") String patientUuid) {
        List<ExerciseRecord>  patientExerciseRecords = exerciseRecordRepository.findAllByPatientUuid(patientUuid);
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
        patientExercisesExcelDataReply.setExerciseRecordId(exerciseRecord.getId());
        patientExercisesExcelDataReply.setExerciseId(exerciseRecord.getExerciseId());
        patientExercisesExcelDataReply.setExerciseName(exerciseRecord.getExercise().getName());
        patientExercisesExcelDataReply.setAngle(excelDataEntry.getAngle());
        patientExercisesExcelDataReply.setTimeStamp(new Date(excelDataEntry.getTimestamp().getTime()));
        patientExercisesExcelDataReply.setExerciseTypeId(exerciseRecord.getExercise().getExerciseType().getId());
        patientExercisesExcelDataReply.setExerciseTypeName(exerciseRecord.getExercise().getExerciseType().getName());

        return patientExercisesExcelDataReply;
    }



}
