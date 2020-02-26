package hackathon.server.dal.crud;


import hackathon.server.models.api.ProtocolShortDataReply;
import hackathon.server.models.db.ExerciseRecord;
import hackathon.server.models.db.Protocol;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProtocolRepository extends CrudRepository<Protocol, Long> {

    List<Protocol> findAll();

    List<ProtocolShortDataReply> findAllShort();
}
