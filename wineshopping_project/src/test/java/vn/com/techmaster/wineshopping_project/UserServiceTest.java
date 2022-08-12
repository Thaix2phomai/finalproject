package vn.com.techmaster.wineshopping_project;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import vn.com.techmaster.wineshopping_project.hash.Hashing;
import vn.com.techmaster.wineshopping_project.model.Role;
import vn.com.techmaster.wineshopping_project.model.State;
import vn.com.techmaster.wineshopping_project.model.User;
import vn.com.techmaster.wineshopping_project.service.UserService;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private Hashing hashing;

    protected MockHttpSession session;


    @Test
    public void test_login() {

        String email = "Thai123@gmail.com";
        String pass = "Thai123";
        User user = userService.login(email, pass);

        assertThat(user).isNotNull();
    }

    @Test
    public void test_register() {
        String email = "Thai1234@gmail.com";
        String pass = "Thai123";
        String phone = "0394106474";
        String name = "Thai";

        User user = userService.addUser(name, email, phone, pass);

        assertThat(user).isNotNull();
        assertThat(user.getState()).isEqualTo(State.PENDING);

    }

    @Test
    public void test_changePass() {
//        User user = User.builder().id("u5").userName("Thái").hashPassword(hashing.hashPassword("Thai123")).email("Thai123@gmail.com").address("Hà Nội").phone("0989980116").role(Role.Admin).state(State.ACTIVE).build();
//        session.setAttribute("user", user);
//        User userNew = userService.changPassword("Thai123", "Thai1234", session);
//        assertThat(userNew.getHashPassword()).isEqualTo(hashing.hashPassword("Thai1234"));

    }


}
