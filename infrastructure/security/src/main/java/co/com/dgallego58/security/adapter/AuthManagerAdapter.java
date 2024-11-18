package co.com.dgallego58.security.adapter;

import co.com.dgallego58.domain.access.model.AuthHandler;

import co.com.dgallego58.security.filter.JwtControl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthManagerAdapter implements AuthHandler {

    private final AuthenticationManager authenticationManager;
    private final JwtControl jwtControl;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String authenticate(String username, String password) {
        var authReq = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        var authResp = authenticationManager.authenticate(authReq);
        return jwtControl.encode(authResp.getPrincipal());
    }


    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
