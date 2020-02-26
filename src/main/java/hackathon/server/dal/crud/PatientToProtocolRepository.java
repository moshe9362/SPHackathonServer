package hackathon.server.dal.crud;


import hackathon.server.models.db.ExcelData;
import hackathon.server.models.db.PatientToProtocol;
import hackathon.server.models.db.PatientToProtocolPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientToProtocolRepository extends CrudRepository<PatientToProtocol, PatientToProtocolPK> {

    List<PatientToProtocol> findAll();

}
