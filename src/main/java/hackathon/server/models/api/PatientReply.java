package hackathon.server.models.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import hackathon.server.models.api.enums.Gender;
import lombok.Data;

import java.util.Date;

@Data
public class PatientReply {
    private String uuid;
    private String idNumber;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private Gender gender;
    private String email;
    private String phone;


}
