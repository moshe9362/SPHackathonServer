package hackathon.server.controllers;

import hackathon.server.models.MockEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class ClientController {

    @PostMapping("/user/signUp")
    public MockEntity signUp(@RequestParam("id") String id,
                             @RequestParam("firstName") String FirstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("birthDate") String birthDate,
                             @RequestParam("gender") int gender,
                             @RequestParam("password") String password) {
        String encodedPassword = DigestUtils.sha256Hex(password);



        return new MockEntity();
    }

    @PostMapping("/user/login")
    public MockEntity login(@RequestParam("id") String id,
                            @RequestParam("password") String password) {
        String encodedPassword = DigestUtils.sha256Hex(password);


        return new MockEntity();
    }

}
