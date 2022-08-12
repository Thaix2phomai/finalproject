package vn.com.techmaster.wineshopping_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.techmaster.wineshopping_project.model.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, String> {
}
