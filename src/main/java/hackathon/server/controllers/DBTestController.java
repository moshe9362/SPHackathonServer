package hackathon.server.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import hackathon.server.dal.crud.*;
import hackathon.server.models.db.Exercise;
import hackathon.server.models.db.ExerciseType;
import hackathon.server.models.db.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DBTestController {

    private PatientRepository patientRepository;
    private ExerciseRepository exerciseRepository;
    private ExerciseRecordRepository exerciseRecordRepository;
    private ExerciseTypeRepository exerciseTypeRepository;
    private ProtocolRepository protocolRepository;

    @Autowired
    public DBTestController(PatientRepository patientRepository, ExerciseRepository exerciseRepository, ExerciseRecordRepository exerciseRecordRepository, ExerciseTypeRepository exerciseTypeRepository, ProtocolRepository protocolRepository) {
        this.patientRepository = patientRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseRecordRepository = exerciseRecordRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.protocolRepository = protocolRepository;
    }


    @GetMapping("/shaq/create/patient")
    @ResponseBody
    public String createPatient() {
        Patient patient = new Patient();
        patient.setIdNumber("209081900l");
        patient.setLastName("Vitkon");
        patient.setFirstName("Shaked");
        patientRepository.save(patient);
        return "Created Successffuly";
    }

    @GetMapping("/shaq/create/exercise")
    @ResponseBody
    public String createExercise() {
        Exercise exercise = new Exercise();
        exercise.setStartDayInProtocol(1);
        exercise.setEndDayInProtocol(3);
        exercise.setProperties((new Gson()).toJsonTree(exercise));
        exerciseRepository.save(exercise);
        return "Created Successffuly";
    }

    @GetMapping("/shaq/test/double")
    @ResponseBody
    public List<Exercise> doubleeSaveCheck() {
        ExerciseType type = new ExerciseType();
        type.setName("type1");
        type.setPropertiesSchema(new JsonPrimitive("type1"));
        exerciseTypeRepository.save(type);

        ExerciseType exerciseTypeFromDB = exerciseTypeRepository.findAll().get(0);

        Exercise exercise = new Exercise();
        exercise.setName("Shaked");
        exercise.setStartDayInProtocol(1);
        exercise.setEndDayInProtocol(3);
        exercise.setProperties((new Gson()).toJsonTree(exercise));
        exercise.setExerciseType(exerciseTypeFromDB);
        exerciseRepository.save(exercise);

        Exercise exerciseFromDB = exerciseRepository.findAll().get(0);
        exercise.setName("Moshe");
        exerciseRepository.save(exerciseFromDB);

        return exerciseRepository.findAll();
    }

    @GetMapping("/shaq/test")
    @ResponseBody
    public List<String> test() {

        return patientRepository.getAllPatientUuids();
    }

}
