package vn.com.techmaster.wineshopping_project.service;

import vn.com.techmaster.wineshopping_project.model.Comment;
import vn.com.techmaster.wineshopping_project.model.User;
import vn.com.techmaster.wineshopping_project.request.CommentRequest;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface UserService {
    public User login(String email, String password);
    public boolean logout(String email);
    public User addUser(String fullname, String email,String phone, String password);

    public void checkValidate(String code);

    public User updateNewPassword(String email);

    public User changPassword (String oldPass, String newPass, HttpSession session);

    public User unActiveUser (String id);

    public Comment userComment (CommentRequest commentRequest, HttpSession session);
}
