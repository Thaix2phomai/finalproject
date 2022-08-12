package vn.com.techmaster.wineshopping_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Fullname cannot blank")
    private String fullname;
    @NotBlank(message = "Email cannot blank")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message="Please enter your phone number")
    @Size(min = 10, max = 11, message = "Your phone number must between 10 - 11")
    private String phoneNumber;
    @Size(min = 5, max = 20, message = "Password length must beetween 5 and 20 characters")
    private String password;
    @Size(min = 5, max = 20, message = "Password length must beetween 5 and 20 characters")
    private String confirmPassword;


}
