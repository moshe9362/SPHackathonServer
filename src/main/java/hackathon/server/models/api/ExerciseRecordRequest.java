package hackathon.server.models.api;

import com.google.gson.JsonElement;
import lombok.Data;

import java.util.List;

@Data
public class ExerciseRecordRequest {
    private long exerciseId;
    private String userUuid;
    private String startDateOfExercise;
    private String endDateOfExercise;
    private JsonElement exerciseData;
    private List<ExcelDataRequest> excelData;
}
