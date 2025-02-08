package microservice.productservice.com.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${authentication-service.url}")
    private String authServiceUrl;

    private final RestTemplate restTemplate;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtFilter(RestTemplate restTemplate, HandlerExceptionResolver handlerExceptionResolver) {
        this.restTemplate = restTemplate;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String authHeader = request.getHeader("Authorization");

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            restTemplate.exchange(authServiceUrl, HttpMethod.POST, entity, Void.class);
            filterChain.doFilter(request, response);
        } catch (HttpClientErrorException | IOException | ServletException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
