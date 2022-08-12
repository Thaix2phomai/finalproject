package vn.com.techmaster.wineshopping_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.com.techmaster.wineshopping_project.exception.CardException;
import vn.com.techmaster.wineshopping_project.request.CardPaymentRequest;
import vn.com.techmaster.wineshopping_project.service.CartService;
import vn.com.techmaster.wineshopping_project.service.PaymentService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String paymentByCredit(Model model, HttpSession session) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("pay", new CardPaymentRequest("", "", "", ""));
        return "payment";
    }

    @PostMapping
    public String paymentComplete(@Valid @ModelAttribute("pay") CardPaymentRequest cardPaymentRequest, BindingResult result, HttpSession session, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "payment";
        }
        try {
            paymentService.payByMasterCard(session, cardPaymentRequest);
        } catch (CardException ex) {
            switch (ex.getMessage()) {
                case "Your card not exist":
                    result.addError(new FieldError("pay", "cardNumber", "your card number is wrong"));
                    break;
                case "wrongexpirydate":
                    result.addError(new FieldError("pay", "expiryDate", "expiry date is wrong"));
                    break;
                case "wrongcvv":
                    result.addError(new FieldError("pay", "cvv", "cvv is wrong"));
                    break;
                case "wrongname":
                    result.addError(new FieldError("pay", "name", "client name is wrong"));
                    break;

            }
            return "payment";
        }
        redirectAttributes.addFlashAttribute("orderByCard", "order by Master Card success, check your email ");
        return "redirect:/product/page/1";
    }
}
