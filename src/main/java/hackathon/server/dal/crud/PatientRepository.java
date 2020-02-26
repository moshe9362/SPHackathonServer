package hackathon.server.dal.crud;


import hackathon.server.models.db.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, String> {

    List<Patient> findAll();

    Patient findByUuid(String uuid);

    List<Patient> findByIdNumber(String idNumber);

    @Query("select patient.uuid from patient patient")
    List<String> getAllPatientUuids();

}
