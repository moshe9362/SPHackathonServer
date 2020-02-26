package hackathon.server.controllers;

import hackathon.server.dal.DBInserter;
import hackathon.server.models.api.ExcelDataRequest;
import hackathon.server.models.api.ExerciseRecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
public class ClientController {


    private DBInserter DBInserter;

    @Autowired
    public ClientController(DBInserter DBInserter) {
        this.DBInserter = DBInserter;
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