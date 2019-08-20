package io.rainrobot.wake.rest.configuration.security.filter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.rainrobot.wake.rest.configuration.security.SecurityConstants;

public class TokenBuilder {
    public static String create(String username) {

        ZonedDateTime expirationTimeUTC
                = ZonedDateTime.now(ZoneOffset.UTC).plus(
                SecurityConstants.EXPIRATUION_TIME, ChronoUnit.MILLIS);

        return Jwts.builder().setSubject(username)
                .setExpiration(Date.from(expirationTimeUTC.toInstant()))
                .signWith(SignatureAlgorithm.HS256,
                        SecurityConstants.SECRET)
                .compact();
    }
}
