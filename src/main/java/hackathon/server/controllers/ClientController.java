package hackathon.server.controllers;

import hackathon.server.dal.crud.PatientRepository;
import hackathon.server.models.MockEntity;
import hackathon.server.models.db.Patient;
import hackathon.server.models.db.Protocol;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ClientController {

    private PatientRepository patientRepository;
    private Protocol protocol;

    @PostMapping("/client/stab")
    public MockEntity stab() {
        return new MockEntity();
    }

    public List getAllProtocols(){
        List<String> names = patientRepository.getAllProtocolName();
        List<Long> ids = patientRepository.getAllProtocolId();

        Map<String, Long> protocolList = new HashMap<>();

        for (int i = 0; i < ids.size(); i++) {
            protocolList.put(names.get(i), ids.get(i));
        }

        return (List) protocolList;
    }

}
