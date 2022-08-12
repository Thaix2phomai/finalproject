package vn.com.techmaster.wineshopping_project.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    private String id;


    private String name;

    private String description;

    private String detail;

    private String image;

    private String sub_image1;

    private String sub_image2;

    private int quantity;

    private Long price;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime update_at;

    private LocalDateTime create_at;



    @OneToMany( mappedBy = "product",fetch = FetchType.LAZY)
    private List<CartLine> cartLines;


    @OneToMany( mappedBy = "product",fetch = FetchType.LAZY)
    private List<OrderLine> orderLines;

    @OneToMany( mappedBy = "product",fetch = FetchType.LAZY)
    private List<Comment> comments;




}
