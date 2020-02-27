package hackathon.server.controllers;

import hackathon.server.dal.DBInserter;
import hackathon.server.dal.crud.ExerciseRepository;
import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.dal.crud.PatientToProtocolRepository;
import hackathon.server.dal.crud.ProtocolRepository;
import hackathon.server.models.api.*;
import hackathon.server.models.db.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ClientController {
    private PatientRepository patientRepository;
    private PatientToProtocolRepository patientToProtocolRepository;
    private ProtocolRepository protocolRepository;
    private ExerciseRepository exerciseRepository;
    private DBInserter DBInserter;

    public ClientController(DBInserter DBInserter,
                            PatientRepository patientRepository,
                            PatientToProtocolRepository patientToProtocolRepository,
                            ProtocolRepository protocolRepository,
                            ExerciseRepository exerciseRepository) {
        this.DBInserter = DBInserter;
        this.patientRepository = patientRepository;
        this.patientToProtocolRepository = patientToProtocolRepository;
        this.protocolRepository = protocolRepository;
        this.exerciseRepository = exerciseRepository;
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

        if (patientSignUpRequest.getBirthDate() != null) {
            Instant birthDayInstant = Instant.parse(patientSignUpRequest.getBirthDate());
            patient.setBirthDate(new Date(birthDayInstant.toEpochMilli()));
        }

        patient.setUuid(UUID.randomUUID().toString());
        patient.setIdNumber(patientSignUpRequest.getIdNumber());
        patient.setFirstName(patientSignUpRequest.getFirstName());
        patient.setLastName(patientSignUpRequest.getLastName());
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

        PatientToProtocolPK patientToProtocolPK = new PatientToProtocolPK();
        patientToProtocolPK.setPatientUuid(protocolToUserRequest.getUserUuid());
        patientToProtocolPK.setProtocolId(protocolToUserRequest.getProtocolId());
        patientToProtocol.setId(patientToProtocolPK);
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
        return mapProtocols(protocols);
    }

    private List<ProtocolShortDataReply> mapProtocols(List<Protocol> protocols) {
        List<ProtocolShortDataReply> protocolShortDataReplies = new ArrayList<>();

        for (Protocol protocol : protocols) {
            ProtocolShortDataReply protocolShortDataReply = new ProtocolShortDataReply();
            protocolShortDataReply.setProtocolId(protocol.getId());
            protocolShortDataReply.setProtocolName(protocol.getName());
        }

        return protocolShortDataReplies;
    }

    @PostMapping("/exerciseRecords")
    public ResponseEntity<String> exerciseRecords(@RequestBody ExerciseRecordRequest exerciseRecord) {
        DBInserter.insertExerciseRecord(exerciseRecord);
        return ResponseEntity.ok().body(null) ;
    }

    @GetMapping("/protocol/{id}")
    @ResponseBody
    public ProtocolDataReply protocolById(@PathVariable("id") Long id) {
        ProtocolDataReply replyProtocolData = new ProtocolDataReply();
        Protocol protocol = protocolRepository.findById(id).get();
        replyProtocolData.setProtocolId(id);
        replyProtocolData.setProtocolName(protocol.getName());

        List<Exercise> exercises = exerciseRepository.findByProtocolId(id);
        List<ExerciseDataReply> mappedExercises = mapExercises(exercises);
        replyProtocolData.setExercises(mappedExercises);

        return replyProtocolData;
    }

    private List<ExerciseDataReply> mapExercises(List<Exercise> exercises) {
        List<ExerciseDataReply> mappedExercises = new ArrayList<>();

        for(Exercise exercise : exercises){
            ExerciseDataReply mappedExercise = new ExerciseDataReply();
            mappedExercise.setExerciseId(exercise.getId());
            mappedExercise.setExerciseName(exercise.getName());
            mappedExercise.setExerciseTypeId(exercise.getExerciseType().getId());
            mappedExercise.setExerciseTypeName(exercise.getExerciseType().getName());
            mappedExercise.setStartDayInProtocol(exercise.getStartDayInProtocol());
            mappedExercise.setEndDayInProtocol(exercise.getEndDayInProtocol());
            mappedExercise.setProperties(exercise.getProperties());

            mappedExercises.add(mappedExercise);
        }

        return mappedExercises;
    }

}