package vn.com.techmaster.wineshopping_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //https://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File theDir = new File("file:/photos");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        registry.addResourceHandler("/photos/**").addResourceLocations("file:photos/");
    }
}
