package vn.com.techmaster.wineshopping_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import vn.com.techmaster.wineshopping_project.model.*;
import vn.com.techmaster.wineshopping_project.repository.OrderLineRepo;
import vn.com.techmaster.wineshopping_project.repository.OrderRepo;
import vn.com.techmaster.wineshopping_project.repository.ProductRepo;
import vn.com.techmaster.wineshopping_project.request.OrderRequest;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImple implements CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderLineRepo orderLineRepo;

    @Autowired
    private EmailService emailService;

    @Override
    public void addToCart(String id, HttpSession session) {
        HashMap<String, CartLine> cart;

        var rawCart = session.getAttribute("CART"); //Lấy ra session cart

        if (rawCart instanceof HashMap) {
            cart = (HashMap<String, CartLine>) rawCart;
        } else {
            cart = new HashMap<>();
        }
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()) {
            CartLine cartLine = cart.get(id);
            if (cartLine == null) { // sản phẩm chưa có thêm vào hashmap
                cart.put(id, new CartLine(product.get(), 1));
            } else {
                cartLine.increaseByOne(); // sản phẩm đã có tăng lên 1
                cart.put(id, cartLine);
            }
        }

        session.setAttribute("CART", cart); //đặt lại session cho cart
    }

    @Override
    public int countItemInCart(HttpSession session) {
        HashMap<String, CartLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<String, CartLine>) rawCart;
            return cart.values().stream().mapToInt(CartLine::getCount).sum();
        } else {
            return 0;
        }
    }

    @Override
    public Cart getCart(HttpSession session) {
        HashMap<String, CartLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<String, CartLine>) rawCart;
            return new Cart(
                    cart.values().stream().toList(),  //danh sách các mặt hàng mua
                    0.1f, //%Giảm giá
                    true   //Có tính thuế VAT không?
            );
        } else {
            return new Cart();
        }
    }

    @Override
    public void deleteProductInCart(HttpSession session, String id) {
        HashMap<String, CartLine> cart;

        var rawCart = session.getAttribute("CART");
        cart = (HashMap<String, CartLine>) rawCart;
        cart.remove(id);
        session.setAttribute("CART", cart);
    }

    @Override
    public OrderDetail completeOrder(OrderRequest orderRequest, HttpSession session) {


        User user = (User) session.getAttribute("user");
        HashMap<String, CartLine> cart = (HashMap<String, CartLine>) session.getAttribute("CART");

        List<CartLine> cartLineList = cart.values().stream().toList();

        String orderDetailId = UUID.randomUUID().toString();

        Cart rawCart = new Cart(
                cartLineList,  //danh sách các mặt hàng mua
                0.1f, //%Giảm giá
                true   //Có tính thuế VAT không?
        );


        OrderDetail orderDetail = OrderDetail.builder()
                .id(orderDetailId)
                .phoneContact(orderRequest.getPhoneContact())
                .paymentType(orderRequest.getPaymentType())
                .address(orderRequest.getAddressOrder())
                .email(orderRequest.getEmailOrder())
                .orderStatus(OrderStatus.UnDone)
                .total(rawCart.getTotal())
                .user(user)
                .create_at(LocalDateTime.now())
                .update_at(LocalDateTime.now())
                .build();

        // save don hang moi
        orderRepo.save(orderDetail);


        // lấy session cart map vào các bảng
        for (CartLine cartLine : cartLineList) {
            String orderLineId = UUID.randomUUID().toString();

            OrderLine orderLine =
                    OrderLine.builder()
                            .id(orderLineId)
                            .product(cartLine.getProduct())
                            .count(cartLine.getCount())
                            .orderDetail(orderDetail)
                            .create_at(LocalDateTime.now())
                            .update_at(LocalDateTime.now())
                            .build();

            orderLineRepo.save(orderLine);
            // tru san pham trong db product
            Product product = productRepo.findById(cartLine.getProduct().getId()).get();
            ;
            product.setQuantity(product.getQuantity() - cartLine.getCount());
            productRepo.save(product);
        }

        session.removeAttribute("CART");

        emailService.verifyOrder(orderRequest.getEmailOrder());

        return orderDetail;

    }

    @Override
    public OrderDetail orderCheck(String id) {
        Optional<OrderDetail> orderDetail = orderRepo.findById(id);
        OrderDetail currentOrderdetail = orderDetail.get();
        currentOrderdetail.setOrderStatus(OrderStatus.Done);
        orderRepo.save(currentOrderdetail);
        return currentOrderdetail;
    }
}


