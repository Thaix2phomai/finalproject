package vn.com.techmaster.wineshopping_project;

import org.junit.jupiter.api.Test;
import vn.com.techmaster.wineshopping_project.service.StorageService;

import static org.assertj.core.api.Assertions.assertThat;

public class StorageServiceTest {

@Test
public void test_getFileExtension() {
    StorageService s = new StorageService();
    String extension = s.getFileExtension("wine.jpg");
    assertThat(extension).isEqualTo("jpg");

}



}
