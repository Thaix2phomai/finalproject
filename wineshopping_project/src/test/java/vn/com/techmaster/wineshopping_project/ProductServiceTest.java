package vn.com.techmaster.wineshopping_project;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.com.techmaster.wineshopping_project.model.Category;
import vn.com.techmaster.wineshopping_project.model.Product;
import vn.com.techmaster.wineshopping_project.repository.ProductRepo;
import vn.com.techmaster.wineshopping_project.request.SearchRequest;
import vn.com.techmaster.wineshopping_project.service.ProductService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void test_addProduct() {
        Product product = Product.builder().name("Thai123").price(1000L).quantity(15).category(Category.Whyskey).build();
        Product productReturn = productService.add(product);
        assertThat(productReturn).isNotNull();
        assertThat(productReturn.getName()).isEqualTo("Thai123");

    }

    @Test
    public void test_filterProduct() {
        SearchRequest searchRequest = new SearchRequest("Jameson", Category.Whyskey);
        List<Product> a = (List<Product>) productService.filterProduct(searchRequest);
        assertThat(a).isNotNull();
        assertThat(a.size()).isNotNull();

    }

    @Test
    public void test_deleteById() {
        Product product = Product.builder().id("p123").name("thai").build();
        productRepo.save(product);
        productService.deleteById("p123");
        assertThat(productRepo.findById("p123")).isEmpty();
    }


}
