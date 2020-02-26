package hackathon.server.dal;


import hackathon.server.models.db.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, String> {

    List<Patient> findAll();

    Patient findByUuid(String uuid);

}
