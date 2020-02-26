package hackathon.server.models.api;

import lombok.Data;

@Data
public class PatiemtSignUpRequest {
    long idNumber;
    String firstName;
    String lastName;
    String birthDate;
    Gender gender;
    String encryptedPassword;
}
