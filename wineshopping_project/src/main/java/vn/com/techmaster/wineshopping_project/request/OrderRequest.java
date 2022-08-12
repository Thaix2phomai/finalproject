package vn.com.techmaster.wineshopping_project.request;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.techmaster.wineshopping_project.model.PaymentType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    @NotBlank(message = "phone cannot blank")
    @Size(min = 10, max = 11, message = "Your phone number must between 10 - 11")
    private String phoneContact;

    @NotBlank(message = "address cannot blank")
    private String addressOrder;


    @NotBlank(message = "email cannot blank")
    @Email(message = "Invalid email")
    private String emailOrder;

    private PaymentType paymentType;
}
