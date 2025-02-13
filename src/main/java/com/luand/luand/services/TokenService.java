package com.luand.luand.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.luand.luand.entities.dto.TokenDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenService {

        private final JwtEncoder jwtEncoder;
        private final JwtDecoder jwtDecoder;

        private int MINUTES_EXPIRATION_ACCESS_TOKEN = 20;
        private int DAYS_EXPIRATION_REFRESH_TOKEN = 7;
        private static final ZoneId ZONE_ID = ZoneId.of("America/Recife");

        public TokenDTO getTokens(String userIdentifty) {
                var accessToken = generateToken(userIdentifty, getAccessTokenExpirationTime());
                var refreshToken = generateToken(userIdentifty, getRefreshTokenExpirationTime());

                return new TokenDTO(accessToken, refreshToken);
        }

        public TokenDTO refreshAccessToken(String refreshToken) {
                var decodedJwt = jwtDecoder.decode(refreshToken);
                Instant expirationTime = decodedJwt.getExpiresAt();

                if (expirationTime == null || Instant.now().isAfter(expirationTime)) {
                        throw new BadCredentialsException("Refresh token expired, please login again.");
                }

                String userIdentity = decodedJwt.getSubject();
                String newAccessToken = generateToken(userIdentity, getAccessTokenExpirationTime());

                return new TokenDTO(newAccessToken, refreshToken);
        }

        private String generateToken(String userIdentifty, Instant expirationDuration) {

                var claims = JwtClaimsSet.builder()
                                .issuer("luand-backend")
                                .subject(userIdentifty)
                                .issuedAt(getCreationTime())
                                .expiresAt(expirationDuration)
                                .claim("loggedIn", true)
                                .build();

                return jwtEncoder
                                .encode(JwtEncoderParameters.from(claims))
                                .getTokenValue();
        }

        private Instant getCreationTime() {
                return ZonedDateTime.now(ZONE_ID).toInstant();
        }

        private Instant getAccessTokenExpirationTime() {
                return ZonedDateTime.now(ZONE_ID).plusMinutes(MINUTES_EXPIRATION_ACCESS_TOKEN)
                                .toInstant();
        }

        private Instant getRefreshTokenExpirationTime() {
                return ZonedDateTime.now(ZONE_ID).plusDays(DAYS_EXPIRATION_REFRESH_TOKEN)
                                .toInstant();
        }

}
