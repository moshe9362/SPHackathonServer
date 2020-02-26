package hackathon.server.models.api;

import lombok.Data;

@Data
public class ProtocolToUserRequest {
    private String userUuid;
    private Long protocolId;
    private String startDate;
}
