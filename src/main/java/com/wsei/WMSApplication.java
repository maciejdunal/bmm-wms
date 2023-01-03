package com.wsei;

import com.wsei.model.Role;
import com.wsei.model.User;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.wsei.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
/*@EnableSwagger2*/
public class WMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(WMSApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer getCorsConfiguration(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }


    @Bean
/*    @Profile("dev")*/
    CommandLineRunner run(UserService userService) {
        return args -> {
/*
            userService.saveRole(new Role (null, "User"));
            userService.saveRole(new Role(null, "Manager"));
            userService.saveRole(new Role (null, "Read-Only User"));
*/

/*            userService.saveUser(new User(null, "test2@test.com", "aqqaqq123", "testname", "testsurname", null));


            userService.addRoleToUser("test2@test.com", "Manager");*/
        };
    }
}
