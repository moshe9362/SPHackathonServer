package hackathon.server.controllers;

import hackathon.server.dal.DBInserter;
import hackathon.server.models.api.ExcelDataRequest;
import hackathon.server.models.api.ExerciseRecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.dal.crud.PatientToProtocolRepository;
import hackathon.server.dal.crud.ProtocolRepository;
import hackathon.server.models.api.PatientLoginReply;
import hackathon.server.models.api.PatientLoginRequest;
import hackathon.server.models.api.PatientSignUpRequest;
import hackathon.server.models.api.ProtocolToUserRequest;
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
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.xml.ws.Response;
import java.util.List;

@RestController
public class ClientController {
    private PatientRepository patientRepository;
    private PatientToProtocolRepository patientToProtocolRepository;
    private ProtocolRepository protocolRepository;
    private DBInserter DBInserter;

    public ClientController(DBInserter DBInserter,
                            PatientRepository patientRepository,
                            PatientToProtocolRepository patientToProtocolRepository,
                            ProtocolRepository protocolRepository) {
        this.DBInserter = DBInserter;
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
            patientLoginReply.setCurrentProtocolId(patientsToProtocols.get(0).getProtocolId());
        } else {
            patientLoginReply.setCurrentProtocolId(-1);
        }

        patientLoginReply.setPatientUuid(patient.getUuid());

        return patientLoginReply;
    }

    @PostMapping("/user/addProtocolToUser")
    @ResponseBody
    public String addProtocolToUser(@RequestBody ProtocolToUserRequest protocolToUserRequest) {
        List<PatientToProtocol> patientsWithOpenProtocols = patientToProtocolRepository.findByPatientUuidAndEndDate(protocolToUserRequest.getUserUuid(), null);
        for(PatientToProtocol patientToProtocol: patientsWithOpenProtocols) {
            patientToProtocol.setEndDate(new Date(Instant.now().toEpochMilli()));
            patientToProtocolRepository.save(patientToProtocol);
        }

        PatientToProtocol patientToProtocol = new PatientToProtocol();

        patientToProtocol.setPatientUuid(protocolToUserRequest.getUserUuid());
        patientToProtocol.setProtocolId(protocolToUserRequest.getProtocolId());
        patientToProtocol.setStartDate(new Date(Instant.parse(protocolToUserRequest.getStartDate()).toEpochMilli()));
        patientToProtocol.setEndDate(null);

        patientToProtocolRepository.save(patientToProtocol);
        return "ok";
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



    @PostMapping("/exerciseRecords")
    public ResponseEntity exerciseRecords(@RequestBody ExerciseRecordRequest exerciseRecord) {
        DBInserter.insertExerciseRecord(exerciseRecord);
        return new ResponseEntity(HttpStatus.OK) ;
    }

    @GetMapping("/protocol/{id}")
    public String protocolById(@PathVariable("id") String id) {
        return id;
    }
}