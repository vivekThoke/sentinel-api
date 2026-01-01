package com.project.sentinel_api.filter;

import com.project.sentinel_api.model.ApiRequestEvent;
import com.project.sentinel_api.model.Client;
import com.project.sentinel_api.repository.ApiRequestEventRepoistory;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RequestTrackingFilter implements Filter {

    private final ApiRequestEventRepoistory apiRequestEventRepoistory;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        filterChain.doFilter(servletRequest, servletResponse);

        Client client = (Client) httpRequest.getAttribute("client");

        if (client == null) return; // non-authenticated or non-API request

        ApiRequestEvent event = ApiRequestEvent.builder()
                .clientId(client.getId())
                .endPoint(httpRequest.getRequestURI())
                .httpMethod(httpRequest.getMethod())
                .ipAddress(resolveIp(httpRequest))
                .userAgent(httpRequest.getHeader("User-Agent"))
                .timestamp(Instant.now())
                .build();


        apiRequestEventRepoistory.save(event);
    }

    private String resolveIp(HttpServletRequest request){
        String forwarded = request.getHeader("X-Forwarded-For");

        if (forwarded != null && !forwarded.isBlank()){
            return forwarded.split(",")[0];
        }

        return request.getRemoteAddr();
    }
}
