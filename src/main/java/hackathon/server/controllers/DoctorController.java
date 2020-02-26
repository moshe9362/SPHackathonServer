package hackathon.server.controllers;

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



}
