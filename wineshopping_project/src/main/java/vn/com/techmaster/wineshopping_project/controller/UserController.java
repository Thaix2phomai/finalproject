package vn.com.techmaster.wineshopping_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.com.techmaster.wineshopping_project.exception.UserException;
import vn.com.techmaster.wineshopping_project.repository.UserRepo;
import vn.com.techmaster.wineshopping_project.request.ChangepassRequest;
import vn.com.techmaster.wineshopping_project.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listAllUsers(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    //customer
    @GetMapping("customer/change_password")
    public String formChangePassword(Model model) {
        model.addAttribute("changepassRequest", new ChangepassRequest("", "", ""));
        return "change_password";
    }

    @PostMapping("customer/change_password")
    public String changePasswod(@Valid @ModelAttribute("changepassRequest") ChangepassRequest changepassRequest, BindingResult result, HttpSession session , RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "change_password";
        }
        if (!changepassRequest.getNewPass().equals(changepassRequest.getConfirmNewPass())) {
            result.addError(new FieldError("changepassRequest", "confirmNewPass", "Mật khẩu không trùng nhau"));
            return "change_password";
        }
        try {
            userService.changPassword(changepassRequest.getOldPass(), changepassRequest.getNewPass(), session);
        } catch (UserException ex) {
            switch (ex.getMessage()) {
                case "oldPassword and newPassword cannot be the same":
                    result.addError(new FieldError("changepassRequest", "newPass", "your new pass cannot be the same your old pass"));
                    break;
                case "Your oldPassword is wrong":
                    result.addError(new FieldError("changepassRequest", "oldPass", "your old pass word is wrong"));

            }
            return "change_password";
        }
        redirectAttributes.addFlashAttribute("changePassSuccess", "change password success");
        return "redirect:/product/page/1";
    }

    //admin
    @GetMapping("/deleteUser/{id}")
    public String removeUser (@PathVariable String id) {
        userService.unActiveUser(id);
        return "redirect:/user";
    }


}
