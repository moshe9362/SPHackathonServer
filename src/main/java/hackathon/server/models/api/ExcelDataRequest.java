package hackathon.server.models.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ExcelDataRequest {
    private String timeStamp;
    @NotBlank(message = "angle is mandatory")
    private float angle;
}
