package hackathon.server.models.api;

import lombok.Value;

@Value
public class ProtocolDataReply {
    long protocolId;
    String protocolName;
    ExerciseDataReply[] exercises;
}
