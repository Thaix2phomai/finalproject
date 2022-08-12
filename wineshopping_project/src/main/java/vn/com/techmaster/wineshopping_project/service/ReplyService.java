package vn.com.techmaster.wineshopping_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.techmaster.wineshopping_project.model.Reply;
import vn.com.techmaster.wineshopping_project.repository.ReplyRepo;

import java.util.UUID;

@Service
public class ReplyService {

    @Autowired private EmailService emailService;

    @Autowired private ReplyRepo replyRepo;


    public Reply creatNewReply(String email, String reply){
        String id = UUID.randomUUID().toString();
        Reply replyMessage = Reply.builder().id(id).email(email).reply(reply).build();
        replyRepo.save(replyMessage);
        emailService.replyMessage(email, reply);
        return replyMessage;
    }
}
