package vn.com.techmaster.wineshopping_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.com.techmaster.wineshopping_project.exception.UserException;
import vn.com.techmaster.wineshopping_project.model.User;
import vn.com.techmaster.wineshopping_project.request.ChangepassRequest;
import vn.com.techmaster.wineshopping_project.request.LoginRequest;
import vn.com.techmaster.wineshopping_project.request.RegisterRequest;
import vn.com.techmaster.wineshopping_project.request.ForgetpassRequest;
import vn.com.techmaster.wineshopping_project.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping
    public String ShowLogIn(Model model) {
        model.addAttribute("loginrequest", new LoginRequest("", ""));
        return "login";
    }


    @PostMapping()
    public String handleLogin(@Valid @ModelAttribute("loginrequest") LoginRequest loginRequest,
                              BindingResult result,
                              HttpSession session, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "login";
        }
        User user;
        try {
            user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("success", "Login Success");
            return "redirect:/product/page/1";
        } catch (UserException ex) {
            switch (ex.getMessage()) {
                case "User is not found":
                    result.addError(new FieldError("loginrequest", "email", "Email does not exist"));
                    break;
                case "User is not activated":
                    result.addError(new FieldError("loginrequest", "email", "User is not activated"));
                    break;
                case "Password is incorrect":
                    result.addError(new FieldError("loginrequest", "password", "Password is incorrect"));
                    break;
            }

            return "login";
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("CART");
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("register")
    public String showRegister(Model model) {
        model.addAttribute("registerrequest", new RegisterRequest("", "", "", "", ""));
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("registerrequest") RegisterRequest registerRequest,
                               BindingResult result, RedirectAttributes redirectAttributes) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            result.addError(new FieldError("registerrequest", "confirmPassword", "Mật khẩu không trùng nhau"));
            return "register";
        }
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.addUser(registerRequest.getFullname(), registerRequest.getEmail(), registerRequest.getPhoneNumber(), registerRequest.getPassword());
        } catch (UserException e) {
            result.addError(new FieldError("register", "email", e.getMessage()));
            return "register";
        }
        redirectAttributes.addFlashAttribute("registersuccess", "register success, check your email");
        return "redirect:/product/page/1";
    }


    @GetMapping("/validate/{register-code}")
    public String validateUser(@PathVariable("register-code") String code) {
        userService.checkValidate(code);
        return "active";
    }

    @GetMapping("forget_password")
    public String forgetPassword(Model model) {
        model.addAttribute("forgetpassRequest", new ForgetpassRequest(""));
        return "forget_password";
    }

    @PostMapping("forget_password")
    public String newPassword(@Valid @ModelAttribute("forgetpassRequest") ForgetpassRequest forgetpassRequest, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "forget_password";
        }
        try {
            userService.updateNewPassword(forgetpassRequest.getEmail());
            redirectAttributes.addFlashAttribute("sendnewpass", "we just send you your new Password, check your email");
        } catch (UserException ex) {
            result.addError(new FieldError("forgetpassRequest", "email", "Email does not exist"));
            return "forget_password";
        }

        return "redirect:/product/page/1";
    }


}
