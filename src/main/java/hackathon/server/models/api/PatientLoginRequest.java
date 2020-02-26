package hackathon.server.models.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PatientLoginRequest {
    @NotBlank(message = "userIdNumber is mandatory")
    private String userIdNumber;
    @NotBlank(message = "password is mandatory")
    private String password;
}
