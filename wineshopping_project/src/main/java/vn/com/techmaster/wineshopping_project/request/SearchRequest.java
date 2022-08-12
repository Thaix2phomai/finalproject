package vn.com.techmaster.wineshopping_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.techmaster.wineshopping_project.model.Category;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest  {
    private String name;
    private Category category;
}
