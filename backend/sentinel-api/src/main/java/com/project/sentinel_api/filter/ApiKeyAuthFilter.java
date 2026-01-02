package com.project.sentinel_api.filter;

import com.project.sentinel_api.model.Client;
import com.project.sentinel_api.repository.ClientRepoistory;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter implements Filter {
    private final ClientRepoistory clientRepoistory;
    private static final String API_KEY_HEADER = "X-API-KEY";

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String apiKey = httpRequest.getHeader(API_KEY_HEADER);

        if (apiKey == null || apiKey.isBlank()){
            reject(httpResponse, "Missing API Key");
            return;
        }

        Optional<Client> clientOpt = clientRepoistory.findByApiKey(apiKey);

        if(clientOpt.isEmpty() || clientOpt.get().getStatus() != Client.Status.ACTIVE){
            reject(httpResponse, "Invalid or Inactive API key");
            return;
        }

        httpRequest.setAttribute("client", clientOpt.get());

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void reject(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write("""
                {
                    "error": "%s"
                }
                """.formatted(message));
    }
}
