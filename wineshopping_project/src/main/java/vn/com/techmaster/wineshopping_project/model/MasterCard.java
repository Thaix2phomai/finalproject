package vn.com.techmaster.wineshopping_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterCard {
    @Id
    private String id;

    private String cardNumber;

    private String expiryDate;

    private String cvv;

    private String name;

}
