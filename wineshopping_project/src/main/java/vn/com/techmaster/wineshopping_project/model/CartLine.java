package vn.com.techmaster.wineshopping_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartLine implements Serializable {

    @Id
    private String id;
    private int count;

    private LocalDateTime update_at;

    private LocalDateTime create_at;


    public CartLine(Product product, int i) {
        this.product = product;
        this.count = i;
    }


    public void increaseByOne()  {
        this.count += 1;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cartId")
    private Cart cart;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;



}
