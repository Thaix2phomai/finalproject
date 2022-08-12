package vn.com.techmaster.wineshopping_project.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetail {
    @Id
    private String id;

    private Long total;

    private  String email;

    private String phoneContact;

    private String address;

//    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime update_at;

    private LocalDateTime create_at;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;


    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    @OneToMany(mappedBy = "orderDetail", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;





}
