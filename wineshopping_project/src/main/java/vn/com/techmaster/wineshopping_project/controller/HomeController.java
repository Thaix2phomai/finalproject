package vn.com.techmaster.wineshopping_project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.com.techmaster.wineshopping_project.repository.ProductRepo;
import vn.com.techmaster.wineshopping_project.service.CartService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired private ProductRepo productRepo;

    @Autowired private CartService cartService;

    @GetMapping
    public String ShowHomePage (Model model, HttpSession session){
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("cartCount", cartService.countItemInCart(session));
        return "index";
    }
}
