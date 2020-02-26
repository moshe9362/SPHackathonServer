package hackathon.server.controllers;

import com.google.gson.Gson;
import hackathon.server.dal.crud.ExerciseRepository;
import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.db.Exercise;
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

    @Autowired
    public DBTestController(PatientRepository patientRepository, ExerciseRepository exerciseRepository) {
        this.patientRepository = patientRepository;
        this.exerciseRepository = exerciseRepository;
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

    @GetMapping("/shaq/test")
    @ResponseBody
    public List<String> test() {

        return patientRepository.getAllPatientUuids();
    }

}
