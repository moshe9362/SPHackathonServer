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

import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorController {

    private ExerciseRecordRepository exerciseRepository;

    @Autowired
    public DoctorController(ExerciseRecordRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping("/patient")
    public List<Integer> getAllPatients() {
        //TODO add logic
        return new ArrayList<>();
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