package com.luand.luand.services;

import java.time.Instant;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.luand.luand.entities.dto.user.LoginDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtEncoder jwtEncoder;

    public String authenticate(LoginDTO data) {
        var user = userService.getUserByEmail(data.email());

        var isValid = userService.validatePassword(data.password(), user.getPassword());
        if (!isValid) {
            throw new BadCredentialsException("Invalid password");
        }

        return generateToken(user.getId().toString());
    }

    private String generateToken(String userIdentifty) {
        var claims = generateClaims(userIdentifty);
        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    private JwtClaimsSet generateClaims(String userIdentifty) {
        var now = Instant.now();
        var expirationTime = 3600L;

        var claims = JwtClaimsSet.builder()
                .issuer("luand-backend")
                .subject(userIdentifty)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationTime))
                .claim("loggedIn", true)
                .build();

        return claims;
    }
}
