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
import hackathon.server.models.api.PatientLoginReply;
import hackathon.server.models.api.PatientLoginRequest;
import hackathon.server.models.api.PatientSignUpRequest;
import hackathon.server.models.db.Patient;
import hackathon.server.models.db.PatientToProtocol;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.xml.ws.Response;
import java.util.List;

@RestController
public class ClientController {
    private PatientRepository patientRepository;
    private PatientToProtocolRepository patientToProtocolRepository;

    private DBInserter DBInserter;

    @Autowired
    public ClientController(DBInserter DBInserter,
                            (PatientRepository patientRepository,
                            PatientToProtocolRepository patientToProtocolRepository) {
        this.DBInserter = DBInserter;
        this.patientRepository = patientRepository;
        this.patientToProtocolRepository = patientToProtocolRepository;
    }

    @PostMapping("/user/signUp")
    public ResponseEntity<String> signUp(@RequestBody PatientSignUpRequest patientSignUpRequest) {


        List<Patient> patientList = patientRepository.findByIdNumber(patientSignUpRequest.getIdNumber());
        if (!patientList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user already exist!");
        }

        String encodedPassword = DigestUtils.sha256Hex(patientSignUpRequest.getPassword());
        Instant birthDayInstant = Instant.parse(patientSignUpRequest.getBirthDate());
        Patient patient = new Patient();
        patient.setUuid(UUID.randomUUID().toString());
        patient.setIdNumber(patientSignUpRequest.getIdNumber());
        patient.setFirstName(patientSignUpRequest.getFirstName());
        patient.setLastName(patientSignUpRequest.getLastName());
        patient.setBirthDate(new Date(birthDayInstant.toEpochMilli()));
        patient.setGender(patientSignUpRequest.getGender().getValue());
        patient.setPassword(encodedPassword);
        patient.setPhone(patientSignUpRequest.getPhoneNumber());
        patient.setEmail(patientSignUpRequest.getEmail());

        patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/user/login")
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

        patientLoginReply.setCurrentProtocolId(patientsToProtocols.get(0).getProtocol().getId());
        patientLoginReply.setPatientUuid(patient.getUuid());

        return patientLoginReply;
    }


    @PostMapping("/exerciseRecords")
    public ResponseEntity exerciseRecords(@RequestBody ExerciseRecordRequest exerciseRecord) {
        DBInserter.insertExerciseRecord(exerciseRecord);
        //DBInserter.insertExcelData(exerciseRecord.getExcelData());
        return new ResponseEntity(HttpStatus.OK) ;
    }

    @GetMapping("/protocol/{id}")
    public String protocolById(@PathVariable("id") String id) {
        return id;
    }
}