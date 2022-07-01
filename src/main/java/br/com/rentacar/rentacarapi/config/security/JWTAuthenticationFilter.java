package br.com.rentacar.rentacarapi.config.security;

import br.com.rentacar.rentacarapi.controller.handler.exception.BadRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(AUTHORIZATION);
        if (authorization != null) {
            String jwtToken = authorization.replace("Bearer ", "");
            Authentication auth = getAuthenticationFromJwt(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAuthenticationFromJwt(String jwtToken) throws JsonProcessingException {
        try {
            String decodedJwtToken = getDecodedJwtToken(jwtToken);
            return mapper.readValue(decodedJwtToken, JwtUser.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BadRequestException("Invalid jwt token.");
        }
    }

    private String getDecodedJwtToken(String jwtToken) {
        var decoder = Base64.getDecoder();
        var payload = jwtToken.split("\\.");
        byte[] decodedJwt = decoder.decode(payload[1]);
        return new String(decodedJwt);
    }
}
