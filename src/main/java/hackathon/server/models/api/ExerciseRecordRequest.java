package hackathon.server.models.api;

import com.google.gson.JsonElement;
import lombok.Value;

@Value
public class ExerciseRecordRequest {
    long exerciseId;
    String userUuid;
    String startDateOfExercise;
    String endDateOfExercise;
    JsonElement exerciseData;
    ExcelDataRequest[] excelData;
}
