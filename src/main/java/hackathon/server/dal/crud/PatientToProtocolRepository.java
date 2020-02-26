package hackathon.server.dal.crud;


import hackathon.server.models.db.PatientToProtocol;
import hackathon.server.models.db.PatientToProtocolPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface PatientToProtocolRepository extends CrudRepository<PatientToProtocol, PatientToProtocolPK> {

    List<PatientToProtocol> findAll();

    List<PatientToProtocol> findByPatientUuidAndEndDate(String patiendUuid, Date endDate);
}
