package hackathon.server.controllers;

import hackathon.server.models.MockEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorController {

    @GetMapping("/patient")
    public List<Integer> getAllPatients() {
        //TODO add logic
        return new ArrayList<>();
    }



}
