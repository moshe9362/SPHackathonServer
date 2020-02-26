package hackathon.server.models.api;

import lombok.Value;

@Value
public class ProtocolShortDataReply {
    Long protocolId;
    String protocolName;
}
