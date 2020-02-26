package hackathon.server.models.api;

import com.google.gson.JsonElement;
import lombok.Value;

@Value
public class ExerciseDataReply {
    long exerciseId;
    String exerciseName;
    long exerciseTypeId;
    String exerciseTypeName;
    int startDayInProtocol;
    int endDayInProtocol;
    JsonElement properties;
}
