package hackathon.server.models.db;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.sql.Date;

@Data
@Entity(name = "patient_to_protocol")
public class PatientToProtocol {

    @EmbeddedId
    private PatientToProtocolPK id;

    @Column(name = "patient_uuid" ,insertable = false,updatable = false)
    private String patientUuid;

    @Column(name = "protocol_id" ,insertable = false,updatable = false)
    private Long protocolId;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

}
