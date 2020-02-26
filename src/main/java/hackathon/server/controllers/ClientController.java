package hackathon.server.controllers;

import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.dal.crud.PatientToProtocolRepository;
import hackathon.server.dal.crud.ProtocolRepository;
import hackathon.server.models.api.PatientLoginReply;
import hackathon.server.models.api.PatientLoginRequest;
import hackathon.server.models.api.PatientSignUpRequest;
import hackathon.server.models.api.ProtocolShortDataReply;
import hackathon.server.models.db.Patient;
import hackathon.server.models.db.PatientToProtocol;
import hackathon.server.models.db.Protocol;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ClientController {
    private PatientRepository patientRepository;
    private PatientToProtocolRepository patientToProtocolRepository;
    private ProtocolRepository protocolRepository;

    @Autowired
    public ClientController(PatientRepository patientRepository,
                            PatientToProtocolRepository patientToProtocolRepository,
                            ProtocolRepository protocolRepository) {
        this.patientRepository = patientRepository;
        this.patientToProtocolRepository = patientToProtocolRepository;
        this.protocolRepository = protocolRepository;
    }

    @PostMapping("/user/signUp")
    @ResponseBody
    public String signUp(@RequestBody PatientSignUpRequest patientSignUpRequest) {


        List<Patient> patientList = patientRepository.findByIdNumber(patientSignUpRequest.getIdNumber());
        if (!patientList.isEmpty()) {
            throw new RuntimeException("user already exist");
        }

        String encodedPassword = DigestUtils.sha256Hex(patientSignUpRequest.getPassword());
        if (patientSignUpRequest.getBirthDate() != null) {
            Instant birthDayInstant = Instant.parse(patientSignUpRequest.getBirthDate());
        }
        Patient patient = new Patient();
        patient.setUuid(UUID.randomUUID().toString());
        patient.setIdNumber(patientSignUpRequest.getIdNumber());
        patient.setFirstName(patientSignUpRequest.getFirstName());
        patient.setLastName(patientSignUpRequest.getLastName());
        if (patientSignUpRequest.getBirthDate() != null) {
            Instant birthDayInstant = Instant.parse(patientSignUpRequest.getBirthDate());
            patient.setBirthDate(new Date(birthDayInstant.toEpochMilli()));
        }

        patient.setGender(patientSignUpRequest.getGender().getValue());
        patient.setPassword(encodedPassword);
        patient.setPhone(patientSignUpRequest.getPhoneNumber());
        patient.setEmail(patientSignUpRequest.getEmail());

        patientRepository.save(patient);
        return "ok";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public PatientLoginReply login(@RequestBody PatientLoginRequest patientLoginRequest) {

        List<Patient> patients = patientRepository.findByIdNumber(patientLoginRequest.getUserIdNumber());
        if (patients.size() > 1) {
            throw new RuntimeException("Initial server error, there is more then one user with this id. Contact the support");
        }

        if (patients.size() == 0) {
            throw new RuntimeException("User does not exist");
        }

        Patient patient = patients.get(0);
        String encodedPassword = DigestUtils.sha256Hex(patientLoginRequest.getPassword());
        if (!encodedPassword.equals(patient.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }

        PatientLoginReply patientLoginReply = new PatientLoginReply();
        List<PatientToProtocol> patientsToProtocols = patientToProtocolRepository.findByPatientUuidAndEndDate(
                patient.getUuid(), null);

        if (!patientsToProtocols.isEmpty()) {
            patientLoginReply.setCurrentProtocolId(patientsToProtocols.get(0).getProtocol().getId());
        } else {
            patientLoginReply.setCurrentProtocolId(-1);
        }

        patientLoginReply.setPatientUuid(patient.getUuid());

        return patientLoginReply;
    }

    @GetMapping("/protocols")
    @ResponseBody
    public List<ProtocolShortDataReply> getProtocols() {
        List<Protocol> protocols =  protocolRepository.findAll();
        List<ProtocolShortDataReply> protocolShortDataReplies = new ArrayList<>();
        for (Protocol protocol : protocols) {
            ProtocolShortDataReply protocolShortDataReply = new ProtocolShortDataReply();
            protocolShortDataReply.setProtocolId(protocol.getId());
            protocolShortDataReply.setProtocolName(protocol.getName());
        }

        return protocolShortDataReplies;
    }



}
