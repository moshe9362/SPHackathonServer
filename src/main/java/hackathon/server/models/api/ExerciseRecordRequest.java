package hackathon.server.models.api;

import com.google.gson.JsonElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class ExerciseRecordRequest {
    @NotBlank(message = "exerciseId is mandatory")
    private long exerciseId;
    @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")
    private String userUuid;
    @NotBlank(message = "startDateOfExercise is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String startDateOfExercise;
    @NotBlank(message = "endDateOfExercise is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String endDateOfExercise;
    private JsonElement exerciseData;
    private List<ExcelDataRequest> excelData;
}
