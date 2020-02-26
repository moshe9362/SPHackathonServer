package hackathon.server.controllers;

import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.MockEntity;
import hackathon.server.models.api.PatientSignUpRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {
    private PatientRepository patientRepository;

    @Autowired
    public ClientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @PostMapping("/user/signUp")
    public ResponseEntity signUp(@RequestParam("id") String id,
                                 @RequestParam("firstName") String FirstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("birthDate") String birthDate,
                                 @RequestParam("gender") int gender,
                                 @RequestParam("password") String password,
                                 @RequestParam("email") String email,
                                 @RequestParam("phoneNumber") String phoneNumber) {
        PatientSignUpRequest patientSignUpRequest = new PatientSignUpRequest();
        String encodedPassword = DigestUtils.sha256Hex(password);

        if (true) {
            // TODO is exist)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }



        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/user/login")
    public MockEntity login(@RequestParam("id") String id,
                            @RequestParam("password") String password) {
        String encodedPassword = DigestUtils.sha256Hex(password);


        return new MockEntity();
    }

}
