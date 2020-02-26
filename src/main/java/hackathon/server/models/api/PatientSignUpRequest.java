package hackathon.server.models.api;

import hackathon.server.models.api.enums.Gender;
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
