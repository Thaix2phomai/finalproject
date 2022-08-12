package vn.com.techmaster.wineshopping_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangepassRequest {

    @NotBlank(message = "This field can not be blank")
    private String oldPass;

    @NotBlank(message = "This field can not be blank")
    private String newPass;

    @NotBlank(message = "This field can not be blank")
    private String confirmNewPass;


}
