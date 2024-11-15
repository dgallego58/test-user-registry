package co.com.dgallego58.infrastructure.security.filter.jwt;

import co.com.dgallego58.infrastructure.helper.JsonUtil;
import co.com.dgallego58.infrastructure.security.filter.JwtControl;
import co.com.dgallego58.infrastructure.security.filter.JwtValidation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class Sanitizers {


    public static final Sanitizers INSTANCE = new Sanitizers();


    public String secret(){
        var secretVar = System.getenv("JWT_SECRET");

        if (secretVar == null) {
            throw new SchematicJwtException.UnableToLocateSecret("JWT_SECRET is null");
        }
        if (secretVar.length() < 32) {
            throw new SchematicJwtException.InvalidSecret("JWT_SECRET is too short");
        }

        return secretVar;
    }



    public static JwtValidation safeDecoder() {
        var controlCheck = new ControlCheck();
        return new NullCheck(controlCheck);
    }

    public static class Encoder implements JwtControl {

        private final JsonUtil jsonUtil;
        private final JwtParser jwts;

        public Encoder(JsonUtil jsonUtil) {
            this.jsonUtil = jsonUtil;
            this.jwts = Jwts.parser().unsecured().build();
        }

        @Override
        public <T> T decodeNoVerifying(String token, Class<T> expectedObj) {
            Jwe<Claims> claimsJwe = jwts.parseEncryptedClaims(token);
            var json = jsonUtil.toJson(claimsJwe);
            return jsonUtil.fromJson(json, expectedObj);
        }

        @Override
        public String encode(Object payload) {
            var now = Instant.now();
            var oneHourLater = now.plus(Duration.ofHours(1));
            var exp = Date.from(oneHourLater);
            var jsonPayload = jsonUtil.toJson(payload);
            var claims = jsonUtil.toMap(jsonPayload);
            claims.remove("password");

            var sec = INSTANCE.secret();
            return Jwts.builder()
                       .claims(claims)
                       .expiration(exp)
                       .signWith(Keys.hmacShaKeyFor(sec.getBytes(StandardCharsets.UTF_8)))
                       .compact();
        }
    }

    record NullCheck(JwtValidation chain) implements JwtValidation {

        @Override
        public Map<String, Object> validateThenDecode(String token) {
            if (token == null) {
                throw new SchematicJwtException.NullAuthException("Token is null");
            }
            return chain.validateThenDecode(token);
        }
    }

    record ControlCheck(JwtParser jwtParser) implements JwtValidation {


        ControlCheck() {

            this(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(INSTANCE.secret().getBytes(StandardCharsets.UTF_8))).build());
        }

        @Override
        public Map<String, Object> validateThenDecode(String token) {
            try {
                var claimsJwe = jwtParser.parseSignedClaims(token);
                return claimsJwe.getPayload();

            } catch (JwtException e) {
                throw new SchematicJwtException.InvalidJwt(e.getMessage());
            }

        }
    }
}
