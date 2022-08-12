package vn.com.techmaster.wineshopping_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPaymentRequest {

    @NotBlank(message = "This field can not be blank")
    private String cardNumber;

    @NotBlank(message = "This field can not be blank")
    private String expiryDate;

    @NotBlank(message = "This field can not be blank")
    private String cvv;

    @NotBlank(message = "This field can not be blank")
    private String name;
}
