package users.rishik.toDoList.Services;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import users.rishik.toDoList.Security.JwtConfig;
import users.rishik.toDoList.entities.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@ConfigurationProperties(prefix = "jwt")
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    JwtService(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", user.getId());
        map.put("Email", user.getEmail());
        return Jwts.builder()
                .claims(map)
                .subject(user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token){
        return extractAllClaims(token).get("UserId", Long.class);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean Expired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isValidToken(String token, String username){
        if (username.equals(extractUsername(token)) && !this.Expired(token)){
            System.out.println("Token Valid");
            return true;
        }
        System.out.println(token);
        return false;
    }
}
