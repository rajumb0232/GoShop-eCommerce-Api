package edu.goshop_ecommerce.security;

import edu.goshop_ecommerce.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class CustomUserDetailService {

    @Autowired
    private UserRepo userRepo;

    @Bean
    UserDetailsService userDetailsService() {
        return userEmail -> new CustomUserDetails(
                userRepo.findByUserEmail(userEmail)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("Failed to find the User with requested username/email.")
                        )
        );
    }
}
