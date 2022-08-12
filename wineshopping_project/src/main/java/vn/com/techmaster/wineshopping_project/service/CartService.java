package vn.com.techmaster.wineshopping_project.service;

import vn.com.techmaster.wineshopping_project.model.Cart;
import vn.com.techmaster.wineshopping_project.model.OrderDetail;
import vn.com.techmaster.wineshopping_project.request.OrderRequest;

import javax.servlet.http.HttpSession;

public interface CartService {
    public void addToCart(String id, HttpSession session);

    public int countItemInCart(HttpSession session);

    public Cart getCart(HttpSession session);

    public void deleteProductInCart (HttpSession session, String id);

    public OrderDetail completeOrder (OrderRequest orderRequest, HttpSession session);

    public OrderDetail orderCheck (String id);
}
