package hackathon.server.controllers;

import hackathon.server.models.MockEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DoctorController {

    @PostMapping("/doctor/stab")
    public MockEntity stab() {
        return new MockEntity();
    }

}
