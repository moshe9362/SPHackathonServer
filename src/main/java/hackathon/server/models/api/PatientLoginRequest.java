package hackathon.server.models.api;

import lombok.Value;

@Value
public class PatientLoginRequest {
    long userIdNumber;
    String password;
}
