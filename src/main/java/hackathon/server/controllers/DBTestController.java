package hackathon.server.controllers;

import hackathon.server.dal.PatientRepository;
import hackathon.server.models.MockEntity;
import hackathon.server.models.db.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DBTestController {

    private PatientRepository patientRepository;

    @Autowired
    public DBTestController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/shaq/create")
    @ResponseBody
    public String create() {
        Patient patient = new Patient();
        patient.setIdNumber(209081900);
        patient.setLastName("Vitkon");
        patient.setFirstName("Shaked");
        patientRepository.save(patient);
        return "Created Successffuly";
    }

    @GetMapping("/shaq/test")
    @ResponseBody
    public List<Patient> test() {

        return patientRepository.findAll();
    }

}
