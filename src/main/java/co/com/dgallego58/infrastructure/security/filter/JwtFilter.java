package co.com.dgallego58.infrastructure.security.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    public static final String PREFIX = "Bearer ";
    private final JwtValidation jwtValidation;

    public JwtFilter(JwtValidation jwtValidation) {
        this.jwtValidation = jwtValidation;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String jwt;
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(PREFIX)) {
            jwt = authHeader.substring(PREFIX.length());
            var claims = jwtValidation.validateThenDecode(jwt);
            var username = claims.get("username");
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username,
                            null,
                            List.of(new SimpleGrantedAuthority("BASIC")));
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);

    }


}
