package hackathon.server.models.api;

import lombok.Data;

@Data
public class PatientLoginRequest {
    private long userIdNumber;
    private String password;
}
