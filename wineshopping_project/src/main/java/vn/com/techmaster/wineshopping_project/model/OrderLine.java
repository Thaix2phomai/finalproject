package vn.com.techmaster.wineshopping_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class OrderLine {

    @Id
    private String id;

    private int count;

    private LocalDateTime update_at;

    private LocalDateTime create_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orderId")
    private OrderDetail orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;




}
