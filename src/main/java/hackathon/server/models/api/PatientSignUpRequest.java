package hackathon.server.models.api;

import hackathon.server.models.api.enums.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PatientSignUpRequest {
    @NotBlank(message = "Id number is mandatory")
    private String idNumber;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    //@NotBlank(message = "Birth date is mandatory")
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String birthDate;
    @NotBlank(message = "Gender is mandatory")
    private Gender gender;
    @Email(message = "Email must be a valid email address")
    private String email;
    private String phoneNumber;
    @NotBlank(message = "Password is mandatory")
    @Size(min=8, max=16, message="Password must be equal to or greater than 8 characters and less than 16 characters")
    private String password;
}
