package hackathon.server.controllers;

import hackathon.server.dal.crud.ExerciseRecordRepository;
import hackathon.server.models.api.PatientExerciseRecordReply;
import hackathon.server.models.db.ExerciseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.api.PatientReply;
import hackathon.server.models.api.enums.Gender;
import hackathon.server.models.db.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class DoctorController {

    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/patients")
    @ResponseBody
    public ArrayList<PatientReply> getAllPatients() {
        List<Patient> dbResult = patientRepository.findAll();
        ArrayList<PatientReply> result = new ArrayList();
        for (Patient patient : dbResult) {
             result.add(createPatientReply(patient));
        }
        return result;
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
        List<ExerciseRecord> recordsList = exerciseRepository.findByPatientUuid(uuid.toString());
        List<PatientExerciseRecordReply> result = map(recordsList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private List<PatientExerciseRecordReply> map(List<ExerciseRecord> list) {
        List<PatientExerciseRecordReply> a = new ArrayList<>();
        for (ExerciseRecord r : list) {
            PatientExerciseRecordReply b = new PatientExerciseRecordReply();
            b.setExerciseId(r.getExercise().getId());
            b.setExerciseName(r.getExercise().getName());
            b.setExerciseTypeId(r.getExercise().getExerciseType().getId());
            b.setExerciseTypeName(r.getExercise().getExerciseType().getName());

            b.setEndDateTimeOfExercise(r.getEndOfExercise().toString());
            b.setExerciseData(r.getExerciseData());


            b.setId(r.getId());


            b.setStartDateTimeOfExercise(r.getStartOfExercise().toString());
            a.add(b);
        }

        return a;
    }

}