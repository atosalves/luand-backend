package com.luand.luand.services;

import java.time.Instant;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.luand.luand.entities.User;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private static final long EXPIRATION_TIME_IN_SECONDS = 3600L;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(User user) {
        var claims = generateClaims(user.getId().toString());
        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    public Long getExpirationTime() {
        return EXPIRATION_TIME_IN_SECONDS;
    }

    private JwtClaimsSet generateClaims(String userIdentifty) {
        var now = Instant.now();

        var claims = JwtClaimsSet.builder()
                .issuer("luand-backend")
                .subject(userIdentifty)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRATION_TIME_IN_SECONDS))
                .claim("loggedIn", true)
                .build();

        return claims;
    }
}
