package hackathon.server.models.api;

import lombok.Data;

@Data
public class PatientLoginReply {
    private String PatientUuid;
    private long currentProtocolId;
}
