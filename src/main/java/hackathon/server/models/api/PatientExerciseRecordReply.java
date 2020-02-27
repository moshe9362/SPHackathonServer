package hackathon.server.models.api;

import com.google.gson.JsonElement;
import lombok.Data;

@Data
public class PatientExerciseRecordReply {
    private long id;
    private long exerciseId;
    private String exerciseName;
    private long exerciseTypeId;
    private String exerciseTypeName;
    private String startDateTimeOfExercise;
    private String endDateTimeOfExercise;
    private JsonElement exerciseData;
}
