package hackathon.server.controllers;

import hackathon.server.dal.DBInserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.ws.Response;

@RestController
public class ClientController {


    private DBInserter DBInserter;

    @Autowired
    public ClientController(DBInserter DBInserter) {
        this.DBInserter = DBInserter;
    }

    @GetMapping("/exerciseRecords")
    public ResponseEntity exerciseRecords(@RequestBody ExerciseRecordsRequest record) {
        DBInserter.insertExerciseRecord(record);
        DBInserter.insertExcelData(record.getExcelData());
        return new ResponseEntity(HttpStatus.OK) ;
    }

    @GetMapping("/protocol/{id}")
    public String protocolById(@PathVariable("id") String id) {
        return id;
    }
}