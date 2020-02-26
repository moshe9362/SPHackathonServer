package hackathon.server.models.api;

import lombok.Data;

@Data
public class PatientSignUpRequest {
    private long idNumber;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Gender gender;
    private String encryptedPassword;
}
