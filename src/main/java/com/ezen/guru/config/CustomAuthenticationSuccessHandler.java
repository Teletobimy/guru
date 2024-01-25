package com.ezen.guru.config;

import com.ezen.guru.domain.User;
import com.ezen.guru.service.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)throws IOException, ServletException {
        this.setDefaultTargetUrl("/");
        this.setUseReferer(false);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        request.getSession().setAttribute("user", userDetails);

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
