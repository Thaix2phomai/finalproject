package vn.com.techmaster.wineshopping_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.com.techmaster.wineshopping_project.model.Product;
import vn.com.techmaster.wineshopping_project.repository.ProductRepo;
import vn.com.techmaster.wineshopping_project.request.SearchRequest;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductServiceImple implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Collection<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> filterProduct(SearchRequest searchRequest) {
        List<Product> products = (List<Product>) getAll();
        for (int i = 0; i < products.size(); i++) {
            if (searchRequest.getCategory() == null) {
                return products.stream().filter(product -> product.getName().toLowerCase().contains(searchRequest.getName().toLowerCase())).collect(Collectors.toList());
            }
        }
        return products.stream().filter(product -> product.getName().toLowerCase().contains(searchRequest.getName().toLowerCase()) && product.getCategory().toString().equals(searchRequest.getCategory().toString())).collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        productRepo.deleteById(id);
    }

    @Override
    public Product add(Product product) {
        String id = UUID.randomUUID().toString();
        product.setId(id);
        productRepo.save(product);
        return product;
    }

    @Override
    public void updateLogo(String id, String logo_path) {
        Optional<Product> product = productRepo.findById(id);
        product.get().setImage(logo_path);
        productRepo.save(product.get());
    }


    @Override
    public void edit(Product product) {
        productRepo.save(product);
    }

    @Override
    public Page<Product> pagingProduct(Pageable pageable) {
        List<Product> products = (List<Product>) getAll();
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), products.size() );
        final Page<Product> page = new PageImpl<>(products.subList(start, end), pageable, products.size());
        return page;
    }

    @Override
    public List<Product> similarProduct(Product product) {
        List<Product> products = (List<Product>) getAll();
        return products.stream().filter(product1 -> product1.getCategory().toString().equals(product.getCategory().toString())).collect(Collectors.toList());
    }
}

