package vn.com.techmaster.wineshopping_project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.com.techmaster.wineshopping_project.model.Product;
import vn.com.techmaster.wineshopping_project.request.SearchRequest;

import javax.servlet.http.HttpSession;
import java.util.Collection;

public interface ProductService {

    public Collection<Product> getAll();
    public Object filterProduct(SearchRequest searchRequest);

    public void deleteById(String id);

    public Product add(Product product);

    public void edit(Product product);

    public void updateLogo(String id,String logo_path);

    public Page<Product> pagingProduct (Pageable pageable);

    public Object similarProduct(Product product);

}
