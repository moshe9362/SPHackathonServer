package hackathon.server.models.api;

import hackathon.server.models.api.enums.Gender;
import lombok.Data;

@Data
public class PatientSignUpRequest {
    private String idNumber;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String password;
}
