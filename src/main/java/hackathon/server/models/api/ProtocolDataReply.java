package hackathon.server.models.api;

import lombok.Data;

import java.util.List;

@Data
public class ProtocolDataReply {
    private long protocolId;
    private String protocolName;
    private List<ExerciseDataReply> exercises;
}
