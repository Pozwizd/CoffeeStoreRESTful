package spaceLab.service.Imp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import spaceLab.service.JwtService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String SECRET_KEY = "zX1P4jRW7kF2yG9LhZ6V3mN8qX4aQ5bC0rT3nU7vW1yX8fL2mB5dH9eJ6sP0tA1X";

    @Override
    public String extractCustomerEmail(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    @Override
    public String generateAccessToken(UserDetails userDetails){
        long accessExpiration = 86400000;
        return buildToken(new HashMap<>(),userDetails, accessExpiration);
    }
    @Override
    public String generateRefreshToken(UserDetails userDetails){
        long refreshExpiration = 604800000;
        return buildToken(new HashMap<>(),userDetails, refreshExpiration);
    }
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails,long expiration ){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails){
        String email = extractCustomerEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt){
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt).getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
