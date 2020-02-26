package hackathon.server.models.api;

import lombok.Value;

@Value
public class PatientLoginReply {
    String PatientUuid;
    long currentProtocolId;
}
