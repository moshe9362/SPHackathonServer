package hackathon.server.models.api;

import hackathon.server.models.api.enums.Gender;
import lombok.Data;

@Data
public class PatientDataReply {
    private String uuid;
    private String idNumber;
    private String firstName;
    private String ladtName;
    private String birthDate;
    private Gender gender;
    private String email;
    private String phoneNumber;
}
