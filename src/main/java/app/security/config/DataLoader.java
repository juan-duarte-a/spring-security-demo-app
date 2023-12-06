package app.security.config;

import app.security.entity.AppUser;
import app.security.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DataLoader {

    @Bean
    public CommandLineRunner loadDefaultUser(AppUserService appUserService, PasswordEncoder passwordEncoder) {
        return arg -> {
            AppUser user = new AppUser();
            user.setEmail("user@mail.com");
            user.setPassword(passwordEncoder.encode("pass"));
            user.setRole(AppUser.Role.ADMIN);
            appUserService.newUser(user);
        };
    }

}
