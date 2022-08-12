package vn.com.techmaster.wineshopping_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequest {
    private String email;

    @NotBlank (message = "reply cannot be null")
    private String reply;
}
