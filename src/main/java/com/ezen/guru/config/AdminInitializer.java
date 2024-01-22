package com.ezen.guru.config;

import com.ezen.guru.domain.Role;
import com.ezen.guru.domain.User;
import com.ezen.guru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeAdmin();
    }

    private void initializeAdmin(){
        boolean adminExists = userRepository.existsByUserName("ADMIN");
        if(!adminExists){
            User admin = User.builder()
                    .userName("ADMIN")
                    .name("관리자")
                    .password(bCryptPasswordEncoder.encode("1234"))
                    .email("admin@admin.com")
                    .part("ADMIN")
                    .roles(new HashSet<>(Collections.singleton(Role.ADMIN)))
                    .build();

            userRepository.save(admin);
        }
    }
}
