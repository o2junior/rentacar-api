package br.com.rentacar.rentacarapi.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    public Optional<JwtUser> getJwtUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication instanceof JwtUser ?
                Optional.of(((JwtUser) authentication)) :
                Optional.empty();
    }

}
