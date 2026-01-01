package com.project.sentinel_api.filter;

import com.project.sentinel_api.model.Client;
import com.project.sentinel_api.service.RateLimitingService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter implements Filter {
    private final RateLimitingService rateLimitingService;


    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        Client client = (Client) httpRequest.getAttribute("client");

        if (client != null){
            boolean allowed = rateLimitingService.isValid(client.getId());

            if(!allowed){
                httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write("""
                        {
                            "error": "Rate limit exceeded"
                        }
                        """);

                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
