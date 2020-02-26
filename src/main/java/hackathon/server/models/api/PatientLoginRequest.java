package hackathon.server.models.api;

import lombok.Data;

@Data
public class PatientLoginRequest {
    private String userIdNumber;
    private String password;
}
