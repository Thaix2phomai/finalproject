package vn.com.techmaster.wineshopping_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.techmaster.wineshopping_project.Util.RandomPassword;
import vn.com.techmaster.wineshopping_project.exception.UserException;
import vn.com.techmaster.wineshopping_project.hash.Hashing;
import vn.com.techmaster.wineshopping_project.model.*;
import vn.com.techmaster.wineshopping_project.repository.CommentRepo;
import vn.com.techmaster.wineshopping_project.repository.ProductRepo;
import vn.com.techmaster.wineshopping_project.repository.UserRepo;
import vn.com.techmaster.wineshopping_project.request.CommentRequest;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Hashing hashing;

    @Autowired
    private ActiveCodeService activeCodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CommentRepo commentRepo;


    @Override
    public User login(String email, String password) {
        Optional<User> o_user = userRepo.findUsersByEmail(email);
        if (!o_user.isPresent()) {
            throw new UserException("User is not found");
        }

        User user = o_user.get();
        // User muốn login phải có trạng thái Active
        if (user.getState() != State.ACTIVE) {
            throw new UserException("User is not activated");
        }
        // Kiểm tra password
        if (hashing.validatePassword(password, user.getHashPassword())) {
            return user;
        } else {
            throw new UserException("Password is incorrect");
        }
    }

    @Override
    public boolean logout(String email) {
        return false;
    }

    @Override
    public User addUser(String fullname, String email, String phone, String password) {
        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .userName(fullname)
                .email(email)
                .phone(phone)
                .hashPassword(hashing.hashPassword(password))
                .state(State.PENDING)
                .role(Role.Customer)
                .build();
        if (user.getState().equals(State.PENDING)) {
            String regisCode = UUID.randomUUID().toString();
            activeCodeService.addCode(regisCode, id);
            try {
                emailService.sendEmail(email, regisCode);
            } catch (Exception e) {
                userRepo.delete(user);
                throw new UserException("Địa chỉ email của bạn không tồn tại");
            }
        }
        userRepo.save(user);
        return user;
    }


    @Override
    public void checkValidate(String code) {
        User user = userRepo.findById(activeCodeService.getAllActiveCode().get(code)).get();
        user.setState(State.ACTIVE);
        userRepo.save(user);
    }

    @Override
    public User updateNewPassword(String email) {
        Optional<User> user = userRepo.findUsersByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User is not found");
        }
        RandomPassword rand = new RandomPassword();
        String newPassword = rand.randomPassword(8);
        User currentUser = user.get();
        currentUser.setHashPassword(hashing.hashPassword(newPassword));
        userRepo.save(currentUser);
        emailService.newPassword(email, newPassword);
        return currentUser;
    }

    @Override
    public User changPassword(String oldPass, String newPass, HttpSession session) {
        User userSession = (User) session.getAttribute("user");
        Optional<User> userDB = userRepo.findById(userSession.getId());
        User user = userDB.get();
        if (oldPass.equals(newPass)){
            throw new UserException("oldPassword and newPassword cannot be the same");
        }
        if (hashing.validatePassword(oldPass, user.getHashPassword())) {
            user.setHashPassword(hashing.hashPassword(newPass));
            userRepo.save(user);
            return user;
        }
        else {
            throw new UserException("Your oldPassword is wrong");
        }

    }

    @Override
    public User unActiveUser(String id) {
        Optional<User> user = userRepo.findById(id);
        User removeUser = user.get();
        removeUser.setState(State.DISABLED);
        userRepo.save(removeUser);

        return removeUser;
    }

    @Override
    public Comment userComment(CommentRequest commentRequest, HttpSession session) {
        Product currentProduct = (Product) session.getAttribute("product");
        User currentUser = (User) session.getAttribute("user");
        String commentId = UUID.randomUUID().toString();
        Comment comment = Comment.builder().id(commentId).userComment(commentRequest.getComment()).user(currentUser).product(currentProduct).build();
        commentRepo.save(comment);
        return comment;
    }
}


