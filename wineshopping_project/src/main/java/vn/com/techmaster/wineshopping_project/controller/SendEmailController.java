package vn.com.techmaster.wineshopping_project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.com.techmaster.wineshopping_project.model.Contact;
import vn.com.techmaster.wineshopping_project.repository.ContactRepo;
import vn.com.techmaster.wineshopping_project.request.ContactRequest;
import vn.com.techmaster.wineshopping_project.request.ReplyRequest;
import vn.com.techmaster.wineshopping_project.service.ContactService;
import vn.com.techmaster.wineshopping_project.service.ReplyService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/send")
public class SendEmailController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepo contactRepo;


    @Autowired
    private ReplyService replyService;


    // customer
    @GetMapping("customer/contact")
    public String sendEmail(Model model) {
        model.addAttribute("contactRequest", new ContactRequest("", "", "", ""));
        return "contact";
    }


    @PostMapping("customer/contact")
    public String sendMessageToAdmin(@Valid @ModelAttribute("contactRequest") ContactRequest contactRequest, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "contact";
        }
        contactService.addNewMessage(contactRequest);
        redirectAttributes.addFlashAttribute("contactsuccess", "Your message is success, we will contact you soon");
        return "redirect:/product/page/1";
    }


    //admin
    @GetMapping("admin/message")
    public String listAllMessage(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("messages", contactRepo.findAll());
        return "customer_message";
    }

    @GetMapping("admin/reply/{id}")
    public String replyMesssge(Model model, @PathVariable("id") String id, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        Optional<Contact> contact = contactRepo.findById(id);
        if (contact.isPresent()) {
            Contact currentContact = contact.get();
            model.addAttribute("replyRequest", new ReplyRequest (currentContact.getEmail(), "")) ;
        }
        return "reply";

    }

    @PostMapping("admin/reply")
    public String replyMessegeAdmin(@Valid @ModelAttribute("replyRequest") ReplyRequest replyRequest, Model model, HttpSession session, RedirectAttributes redirectAttributes ){
        model.addAttribute("user", session.getAttribute("user"));
        replyService.creatNewReply(replyRequest.getEmail(), replyRequest.getReply());
        redirectAttributes.addFlashAttribute("replySuccess", "reply success");
        return "redirect:/send/admin/message";
    }
}
