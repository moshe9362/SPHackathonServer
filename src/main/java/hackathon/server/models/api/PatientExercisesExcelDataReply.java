package hackathon.server.models.api;

import lombok.Data;

@Data
public class PatientExercisesExcelDataReply {
    private long exerciseId;
    private String exerciseName;
    private long exerciseTypeId;
    private String exerciseTypeName;
    private String timeStamp;
    private int angle;
}
