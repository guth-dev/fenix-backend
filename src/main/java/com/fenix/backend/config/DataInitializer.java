package com.fenix.backend.config;

import com.fenix.backend.domain.entity.Admin;
import com.fenix.backend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .name("Admin")
                    .email("admin@fenix.com")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .acceptedTerms(true)
                    .acceptedTermsAt(LocalDateTime.now())
                    .build();
            adminRepository.save(admin);
            log.info("=== Admin padrão criado: email=admin@fenix.com / senha=admin123 ===");
        }
    }
}
