package hackathon.server.models.db;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PatientToProtocolPK implements Serializable {

    @Column(name = "patient_uuid")
    private String patientUuid;

    @Column(name = "protocol_id")
    private Long protocolId;

}
