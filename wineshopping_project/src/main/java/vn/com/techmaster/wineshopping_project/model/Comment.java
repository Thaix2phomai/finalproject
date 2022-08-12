package vn.com.techmaster.wineshopping_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private String id;

    private String userComment;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_Id")
    private Product product;
}
