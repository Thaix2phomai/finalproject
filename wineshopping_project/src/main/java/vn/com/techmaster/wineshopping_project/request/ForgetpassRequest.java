package vn.com.techmaster.wineshopping_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetpassRequest {
    @NotBlank(message="Email cannot blank")
    @Email(message = "Invalid email")
    private String email;
}
