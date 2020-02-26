package hackathon.server.controllers;

import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.MockEntity;
import hackathon.server.models.db.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ClientController {

    private PatientRepository patientRepository;

    @PostMapping("/client/stab")
    public MockEntity stab() {
        return new MockEntity();
    }


    @GetMapping("/shaq/create/patient")
    @ResponseBody
    public String createPatient() {
        Patient patient = new Patient();
        patient.setIdNumber(209081900l);
        patient.setLastName("Vitkon");
        patient.setFirstName("Shaked");
        patientRepository.save(patient);
        return "Created Successffuly";
    }

    public List getAllProtocols(){
        patientRepository.getAllProtocolName();
        patientRepository.getAllProtocolId();
        return null;
    }

}
