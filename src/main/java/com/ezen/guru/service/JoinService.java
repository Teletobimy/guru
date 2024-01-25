package com.ezen.guru.service;

import com.ezen.guru.domain.Role;
import com.ezen.guru.domain.User;
import com.ezen.guru.dto.JoinDTO;
import com.ezen.guru.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Log4j2
public class JoinService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional
    public void join(JoinDTO joinDTO){
            boolean isUser = userRepository.existsByUserName(joinDTO.getUserName());
            if(isUser){
                return;
            }
           User user = User.builder()
                   .userName(joinDTO.getUserName())
                   .name(joinDTO.getName())
                   .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                   .email(joinDTO.getEmail())
                   .part(joinDTO.getPart())
                   .roles(new HashSet<>(Collections.singleton(Role.USER)))
                   .phone(joinDTO.getPhone())
                   .build();
           userRepository.save(user);
       }
}
