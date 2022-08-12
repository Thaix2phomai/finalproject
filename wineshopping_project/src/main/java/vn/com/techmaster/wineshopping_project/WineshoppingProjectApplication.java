package vn.com.techmaster.wineshopping_project;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.com.techmaster.wineshopping_project.hash.Hashing;
import vn.com.techmaster.wineshopping_project.model.*;
import vn.com.techmaster.wineshopping_project.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootApplication
public class WineshoppingProjectApplication implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private Hashing hashing;

    @Autowired
    private MasterCardRepo masterCardRepo;


    public static void main(String[] args) {
        SpringApplication.run(WineshoppingProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder().id("u1").userName("Thái").hashPassword(hashing.hashPassword("Thai123")).email("Thai123@gmail.com").address("Hà Nội").phone("0989980116").role(Role.Admin).state(State.ACTIVE).build();
        User user2 = User.builder().id("u2").userName("Cường").hashPassword(hashing.hashPassword("Cuong123")).email("Cuong123@gmail.com").address("Hà Nội").phone("0989980116").role(Role.Customer).state(State.ACTIVE).build();

        userRepo.save(user1);
        userRepo.save(user2);


        Product product1 = Product.builder().id("p1").name("Jack Daniel").description("Ngon, phê").price(10000L).quantity(50).category(Category.Whyskey).image("jackdaniel.jpg").sub_image1("jackdaniel2.jpg").sub_image2("jackdaniel3.jpg").build();
        Product product2 = Product.builder().id("p2").name("Corona").description("Mượt, mát").price(15000L).quantity(20).category(Category.Beer).image("corona.jpg").sub_image1("corona2.jpg").sub_image2("corona3.jpg").build();
        Product product3 = Product.builder().id("p3").name("Jameson").description("Béo, tròn").price(20000L).quantity(15).category(Category.Whyskey).image("jameson.jpg").sub_image1("jameson4.jpg").sub_image2("jameson3.jpg").build();
        Product product4 = Product.builder().id("p4").name("Tobaco").description("Ngon, ngậy").price(30000L).quantity(16).category(Category.Cigar).image("cigar5.jpg").sub_image1("cigar6.jpg").sub_image2("cigar7.jpg").build();
        Product product5 = Product.builder().id("p5").name("Macallan").description("Say, Thấm").price(16000L).quantity(17).category(Category.Whyskey).image("macallan.jpg").sub_image1("macallan2.jpg").sub_image2("macallan3.jpg").build();
        Product product6 = Product.builder().id("p6").name("Tequilla").description("Mất trí nhớ").price(18000L).quantity(18).category(Category.Tequila).image("tequilla.jpg").sub_image1("tequilla2.jpg").sub_image2("tequilla3.jpg").build();
        Product product7 = Product.builder().id("p7").name("Botanica").description("Nhẹ, êm").price(32000L).quantity(19).category(Category.Wine).image("wine.jpg").sub_image1("wine2.jpg").sub_image2("wine3.jpg").build();
        Product product8 = Product.builder().id("p8").name("Budweiser").description("Vui vẻ, sành điệu").price(40000L).quantity(20).category(Category.Beer).image("budweiser.jpg").sub_image1("budweiser2.jpg").sub_image2("budweiser3.jpg").build();

        productRepo.save(product1);
        productRepo.save(product2);
        productRepo.save(product3);
        productRepo.save(product4);
        productRepo.save(product5);
        productRepo.save(product6);
        productRepo.save(product7);
        productRepo.save(product8);


        MasterCard masterCard = MasterCard.builder().id("ox1").cardNumber("1111 1111 1111 1111").expiryDate("11/1111").cvv("111").name("thai").build();
        masterCardRepo.save(masterCard);
    }
}



