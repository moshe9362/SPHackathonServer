package hackathon.server.models.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PatientExercisesExcelDataReply {
    private long exerciseRecordId;
    private long exerciseId;
    private String exerciseName;
    private long exerciseTypeId;
    private String exerciseTypeName;
    @JsonFormat(pattern="yyyy-MM-dd HH:MM:SS")
    private Date timeStamp;
    private float angle;
}
