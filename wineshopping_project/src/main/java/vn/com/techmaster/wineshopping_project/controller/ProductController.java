package vn.com.techmaster.wineshopping_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.com.techmaster.wineshopping_project.model.PaymentType;
import vn.com.techmaster.wineshopping_project.model.Product;
import vn.com.techmaster.wineshopping_project.repository.CommentRepo;
import vn.com.techmaster.wineshopping_project.repository.OrderRepo;
import vn.com.techmaster.wineshopping_project.repository.ProductRepo;
import vn.com.techmaster.wineshopping_project.request.CommentRequest;
import vn.com.techmaster.wineshopping_project.request.OrderRequest;
import vn.com.techmaster.wineshopping_project.request.ProductRequest;
import vn.com.techmaster.wineshopping_project.request.SearchRequest;
import vn.com.techmaster.wineshopping_project.service.CartService;
import vn.com.techmaster.wineshopping_project.service.ProductService;
import vn.com.techmaster.wineshopping_project.service.UserService;
import vn.com.techmaster.wineshopping_project.Util.FileUploadUtil;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;


    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepo commentRepo;


    @GetMapping("/page/{pageNumber}")
    public String listAllProducts(@PathVariable("pageNumber") int pageNumber, Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("searchRequest", new SearchRequest("", null));
        int currentPage = pageNumber;
        Page<Product> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 4);
        page = productService.pagingProduct(pageable);
        List<Product> ListProducts = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("products", ListProducts);
        model.addAttribute("cartCount", cartService.countItemInCart(session));

        return "homepage";
    }

    @GetMapping("/search")
    public String searchByName(@ModelAttribute("searchRequest") SearchRequest searchRequest, Model model, HttpSession session) {
        model.addAttribute("cartCount", cartService.countItemInCart(session));
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("products", productService.filterProduct(searchRequest));
        return "search_product";
    }


    @GetMapping(value = "/delete/{id}")
    public String deleteProductByID(@PathVariable String id, HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        productService.deleteById(id);
        return "redirect:/product/page/1";
    }


    @GetMapping(value = "/add")
    public String addProductForm(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("product", new ProductRequest("", "", "", "", null, "", null, "", null, 0, null, null));
        return "product_add";
    }

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public String addProduct(@Valid @ModelAttribute("product") ProductRequest productRequest,
                             BindingResult result) throws IOException {
        if (productRequest.getLogo().getOriginalFilename().isEmpty()) {
            result.addError(new FieldError("product", "logo", "Logo is required"));
        }
        if (productRequest.getLogo2().getOriginalFilename().isEmpty()) {
            result.addError(new FieldError("product", "logo2", "Logo2 is required"));
        }
        if (productRequest.getLogo3().getOriginalFilename().isEmpty()) {
            result.addError(new FieldError("product", "logo3", "Logo3 is required"));
        }
        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "product_add";
        }

        // Thêm vào cơ sở dữ liệu
        Product product = productService.add(Product.builder().name(productRequest.getName()).description(productRequest.getDescription()).quantity(productRequest.getQuantity()).price(productRequest.getPrice()).category(productRequest.getCategory()).build());

        String logo = StringUtils.cleanPath(productRequest.getLogo().getOriginalFilename());
        String logo2 = StringUtils.cleanPath(productRequest.getLogo2().getOriginalFilename());
        String logo3 = StringUtils.cleanPath(productRequest.getLogo3().getOriginalFilename());

        product.setImage(logo);
        product.setSub_image1(logo2);
        product.setSub_image2(logo3);

        productRepo.save(product);

        String uploadDir = "photos/";

        FileUploadUtil.saveFile(uploadDir, logo, productRequest.getLogo());
        FileUploadUtil.saveFile(uploadDir, logo2, productRequest.getLogo2());
        FileUploadUtil.saveFile(uploadDir, logo3, productRequest.getLogo3());

        return "redirect:/product/page/1";
    }


    @GetMapping(value = "/edit/{id}")
    public String editProductId(Model model, @PathVariable("id") String id, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()) {
            Product currentProduct = product.get();
            model.addAttribute("productReq", new ProductRequest(
                    currentProduct.getId(),
                    currentProduct.getName(),
                    currentProduct.getDescription(),
                    currentProduct.getImage(),
                    null,
                    currentProduct.getSub_image1(),
                    null, currentProduct.getSub_image2(),
                    null,
                    currentProduct.getQuantity(),
                    currentProduct.getPrice(),
                    currentProduct.getCategory()));
            model.addAttribute("product", currentProduct);
        }
        return "product_edit";
    }

    @PostMapping(value = "/edit", consumes = {"multipart/form-data"})
    public String editProduct(@Valid @ModelAttribute("productReq") ProductRequest productRequest,
                              BindingResult result,
                              Model model, HttpSession session) throws IOException {
        model.addAttribute("user", session.getAttribute("user"));
        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "product_edit";
        }

        String uploadDir = "photos/";
        String logo = null;
        String logo2 = null;
        String logo3 = null;

        if (!productRequest.getLogo().getOriginalFilename().isEmpty()) {
            logo = StringUtils.cleanPath(productRequest.getLogo().getOriginalFilename());
            FileUploadUtil.saveFile(uploadDir, logo, productRequest.getLogo());
        }
        if (!productRequest.getLogo2().getOriginalFilename().isEmpty()) {
            logo2 = StringUtils.cleanPath(productRequest.getLogo2().getOriginalFilename());
            FileUploadUtil.saveFile(uploadDir, logo2, productRequest.getLogo2());
        }
        if (!productRequest.getLogo3().getOriginalFilename().isEmpty()) {
            logo3 = StringUtils.cleanPath(productRequest.getLogo3().getOriginalFilename());
            FileUploadUtil.saveFile(uploadDir, logo3, productRequest.getLogo3());
        }

        // Cập nhật lại Product
        productService.edit(Product.builder()
                .id(productRequest.getId())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .image(logo == null ? productRequest.getLogo_path() : logo)
                .sub_image1(logo2 == null ? productRequest.getLogo_path2() : logo2)
                .sub_image2(logo3 == null ? productRequest.getLogo_path3() : logo3)
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .build());

        return "redirect:/product/page/1";
    }

    @GetMapping("/buy/{id}")
    public String addToCart(HttpSession httpSession, @PathVariable(name = "id") String id) {
        cartService.addToCart(id, httpSession);
        return "redirect:/product/page/1";
    }


    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("cartCount", cartService.countItemInCart(session));
        model.addAttribute("cart", cartService.getCart(session));
        return "checkout";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProductInCartByID(@PathVariable String id, HttpSession session) {
        cartService.deleteProductInCart(session, id);
        return "redirect:/product/checkout";
    }

    @GetMapping("/order")
    public String orderDetail(Model model) {
        model.addAttribute("orderRequest", new OrderRequest("", "", "", null));
        return "order";
    }

    @PostMapping("/order")
    public String orderAction(@Valid @ModelAttribute("orderRequest") OrderRequest orderRequest, BindingResult result, HttpSession session, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "order";
        }
        if (orderRequest.getPaymentType().toString() == PaymentType.PREPAID.toString()) {
            session.setAttribute("order", orderRequest);
            return "redirect:/payment";
        }
        cartService.completeOrder(orderRequest, session);
        redirectAttributes.addFlashAttribute("orderComplete", "Your order is complete, check your email");
        return "redirect:/product/page/1";
    }


    @GetMapping("/customer_order")
    public String listAllCustomerOrder(Model model, HttpSession session) {
        model.addAttribute("orderbills", orderRepo.findAll());
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("user", session.getAttribute("user"));
        return "customer_order";
    }

    @GetMapping("/check_order/{id}")
    public String orderComplete(@PathVariable String id) {
        cartService.orderCheck(id);
        return "redirect:/product/customer_order";
    }

    @GetMapping("/productdetail/{id}")
    public String productDetail(@PathVariable String id, Model model, HttpSession session) {
        Optional<Product> product = productRepo.findById(id);
        Product currentProduct = product.get();
        model.addAttribute("product", currentProduct);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("cartCount", cartService.countItemInCart(session));
        model.addAttribute("similarProducts", productService.similarProduct(currentProduct));
        model.addAttribute("comments", commentRepo.findAll());
        return "product_detail";
    }

    @GetMapping("/comment/{id}")
    public String productComment(Model model, HttpSession session, @PathVariable String id) {
        Optional<Product> product = productRepo.findById(id);
        Product currentProduct = product.get();
        model.addAttribute("product", currentProduct);
        model.addAttribute("commentRequest", new CommentRequest(""));
        session.setAttribute("product", currentProduct);
        return "comment";
    }

    @PostMapping("/comment")
    public String productComment(@Valid @ModelAttribute("commentRequest") CommentRequest commentRequest, Model model, HttpSession session) {
        userService.userComment(commentRequest, session);
        return "redirect:/product/page/1";
    }

}
