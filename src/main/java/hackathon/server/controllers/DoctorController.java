package hackathon.server.controllers;

import hackathon.server.dal.crud.ExerciseRecordRepository;
import hackathon.server.dal.crud.ExerciseRepository;
import hackathon.server.models.api.PatientExerciseRecordReply;
import hackathon.server.models.db.Exercise;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class DoctorController {

    private PatientRepository patientRepository;
    private ExerciseRepository exerciseRepository;
    private ExerciseRecordRepository exerciseRecordRepository;

    @Autowired
    public DoctorController(PatientRepository patientRepository,
                            ExerciseRepository exerciseRepository,
                            ExerciseRecordRepository exerciseRecordRepository) {
        this.patientRepository = patientRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseRecordRepository = exerciseRecordRepository;
    }

    @GetMapping("/patients")
    @ResponseBody
    public ArrayList<PatientReply> getAllPatients() {
        List<Patient> dbResult = patientRepository.findAll();
        ArrayList<PatientReply> result = new ArrayList<>();
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
        List<ExerciseRecord> recordsList = exerciseRecordRepository.findByPatientUuid(uuid.toString());
        List<PatientExerciseRecordReply> result = map(recordsList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private List<PatientExerciseRecordReply> map(List<ExerciseRecord> list) {
        List<PatientExerciseRecordReply> patientExerciseRecordReplies = new ArrayList<>();
        for (ExerciseRecord r : list) {
            PatientExerciseRecordReply patientExerciseRecordReply = new PatientExerciseRecordReply();
            Exercise exercise = exerciseRepository.findById(r.getExerciseId()).get();
            patientExerciseRecordReply.setExerciseId(exercise.getId());
            patientExerciseRecordReply.setExerciseName(exercise.getName());
            patientExerciseRecordReply.setExerciseTypeId(exercise.getExerciseType().getId());
            patientExerciseRecordReply.setExerciseTypeName(exercise.getExerciseType().getName());

            patientExerciseRecordReply.setEndDateTimeOfExercise(r.getEndOfExercise().toString());
            patientExerciseRecordReply.setExerciseData(r.getExerciseData());

            patientExerciseRecordReply.setId(r.getId());

            patientExerciseRecordReply.setStartDateTimeOfExercise(r.getStartOfExercise().toString());
            patientExerciseRecordReplies.add(patientExerciseRecordReply);
        }

        return patientExerciseRecordReplies;
    }

}