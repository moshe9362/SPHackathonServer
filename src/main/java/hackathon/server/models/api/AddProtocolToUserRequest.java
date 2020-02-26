package hackathon.server.models.api;

import lombok.Value;

@Value
public class AddProtocolToUserRequest {
    String userUuid;
    Long protocolId;
    String startDate;
}
