package hackathon.server.models.db;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class PatientToProtocolPK implements Serializable {

    @Column(name = "patient_uuid")
    private String patientUuid;

    @Column(name = "protocol_id")
    private Long protocolId;

}
