package hackathon.server.models.api;

import com.google.gson.JsonElement;
import lombok.Data;

@Data
public class ExerciseDataReply {
    private long exerciseId;
    private String exerciseName;
    private long exerciseTypeId;
    private String exerciseTypeName;
    private int startDayInProtocol;
    private int endDayInProtocol;
    private JsonElement properties;
}
