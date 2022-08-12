package vn.com.techmaster.wineshopping_project.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
public class Cart implements Serializable {
    @Id
    private String id;

    private Long rawTotal;

    private long discount;

    private long vatTax;

    private long total;

    private LocalDateTime update_at;

    private LocalDateTime create_at;


    @OneToMany(
            mappedBy = "cart",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<CartLine> cartLines;

    public Cart() {
        cartLines = Collections.emptyList();
        rawTotal = 0L;
        discount = 0;
        vatTax = 0;
        total = 0;
    }

    public Cart(List<CartLine> cartLines, double discountPercentage, boolean isVATIncluded) {
        this.cartLines = cartLines;
        rawTotal = 0L;

        cartLines.stream().forEach(cartLine -> {
            rawTotal += cartLine.getCount() * cartLine.getProduct().getPrice();
        });

        discount = (long) Math.round(rawTotal * discountPercentage);

        if (isVATIncluded) {
            vatTax = (long) Math.round((rawTotal - discount) * 0.08f);
        } else {
            vatTax = 0;
        }

        total = rawTotal - discount + vatTax;
    }

    public void addToCart(CartLine cartLine) {
        if (this.cartLines == null) this.cartLines = new ArrayList<>();
        this.cartLines.add(cartLine);
        rawTotal += cartLine.getCount() * cartLine.getProduct().getPrice();

    }


}
