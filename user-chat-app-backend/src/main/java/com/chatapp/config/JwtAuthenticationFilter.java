package com.chatapp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component // Mark this as a Spring bean
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;

    public JwtAuthenticationFilter() {
        // Initialize the secret key (replace with your actual secret key)
        this.secretKey = Keys.hmacShaKeyFor("your-secret-key-1234567890-1234567890-1234567890".getBytes());
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain)
    throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            try {
                token = token.substring(7); // Remove "Bearer " prefix

                // Parse the JWT token
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                // Extract username and authorities from the token
                String username = claims.getSubject();
                List<String> authorities = ((List<?>) claims.get("authorities")).stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

                // Create an Authentication object
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // Invalid token
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Send 401 Unauthorized
                response.getWriter().write("Invalid or expired token"); // Optional: Add a response message
                return; // Stop further processing
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}