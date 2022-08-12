package vn.com.techmaster.wineshopping_project.request;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import vn.com.techmaster.wineshopping_project.model.Category;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private String id;

    @NotBlank(message = "Name cannot null")
    private String name;


    private String description;


    private String logo_path;
    private MultipartFile logo;

    private String logo_path2;
    private MultipartFile logo2;

    private String logo_path3;
    private MultipartFile logo3;

    @Min(value = 1, message = "Quantity should be bigger than 1")
    @Max(value = 100, message = "Quantity should not be greater than 100")
    private int quantity;

    private Long price;

    private Category category;

}
