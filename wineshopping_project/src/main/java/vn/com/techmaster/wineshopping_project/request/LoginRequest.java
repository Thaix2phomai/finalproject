package vn.com.techmaster.wineshopping_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message="Email cannot blank")
    @Email(message = "Invalid email")
    private String email;
    @Size(min=5, max=20, message = "Password length must between 5 and 20 characters")
    private String password;
}
