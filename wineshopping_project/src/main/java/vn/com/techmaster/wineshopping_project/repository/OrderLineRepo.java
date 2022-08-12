package vn.com.techmaster.wineshopping_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.techmaster.wineshopping_project.model.OrderLine;

public interface OrderLineRepo extends JpaRepository<OrderLine, String> {
}
