package com.luand.luand.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.luand.luand.entities.dto.TokenResponseDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenService {

        private final JwtEncoder jwtEncoder;

        private static final int HOURS_EXPIRATION_TOKEN = 20;
        private static final ZoneId ZONE_ID = ZoneId.systemDefault();

        public TokenResponseDTO getToken(String userIdentifty) {
                var token = generateToken(userIdentifty);
                return new TokenResponseDTO(token);
        }

        private String generateToken(String userIdentifty) {
                var claims = JwtClaimsSet.builder()
                                .issuer("luand-backend")
                                .subject(userIdentifty)
                                .issuedAt(getCreationTime())
                                .expiresAt(getExpirationTime())
                                .claim("loggedIn", true)
                                .build();

                return jwtEncoder
                                .encode(JwtEncoderParameters.from(claims))
                                .getTokenValue();
        }

        private Instant getCreationTime() {
                return ZonedDateTime.now(ZONE_ID).toInstant();
        }

        private Instant getExpirationTime() {
                return ZonedDateTime.now(ZONE_ID).plusHours(HOURS_EXPIRATION_TOKEN)
                                .toInstant();
        }
}
