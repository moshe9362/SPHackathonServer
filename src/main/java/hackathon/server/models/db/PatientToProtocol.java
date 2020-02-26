package hackathon.server.models.db;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity(name = "patient_to_protocol")
public class PatientToProtocol {

    @EmbeddedId
    private PatientToProtocolPK id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_uuid" ,insertable = false,updatable = false)
    private Patient patient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "protocol_id" ,insertable = false,updatable = false)
    private Protocol protocol;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

}
