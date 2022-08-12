package vn.com.techmaster.wineshopping_project.request;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactRequest {
    @NotBlank(message = "Tên không được để trống")
    private String fullname;
    @Email(message = "Email sai định dạng")
    @NotBlank(message = "Email không được để trống")
    private String email;
    @NotBlank(message = "Chủ đề không được để trống")
    private String subject;
    @NotBlank(message = "Tin nhắn không được để trống")
    private String message;
}

