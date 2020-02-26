package hackathon.server.controllers;

import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.MockEntity;
import hackathon.server.models.db.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        List<Patient> result = patientRepository.findAll();
        for (Patient patient : result) {
            patient.setPassword(null);
        }
        return result;
    }



}
