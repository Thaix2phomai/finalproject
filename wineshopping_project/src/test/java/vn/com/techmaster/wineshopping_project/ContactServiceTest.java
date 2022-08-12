package vn.com.techmaster.wineshopping_project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.com.techmaster.wineshopping_project.model.Contact;
import vn.com.techmaster.wineshopping_project.request.ContactRequest;
import vn.com.techmaster.wineshopping_project.service.ContactService;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Test
    public void test_addNewMessege () {
        ContactRequest contactRequest = new ContactRequest("Thai", "Thai1211@gmail", "wine", "con chai abc k?");
        Contact contact = contactService.addNewMessage(contactRequest);
        assertThat(contact).isNotNull();
    }


}
