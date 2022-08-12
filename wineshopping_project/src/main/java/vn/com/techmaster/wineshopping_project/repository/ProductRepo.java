package vn.com.techmaster.wineshopping_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.techmaster.wineshopping_project.model.Product;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {

}
