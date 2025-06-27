package best.fufushop.util;

import best.fufushop.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class JwtUtil {
    private final String SECRET = "secret_key_secret_key_secret_key_secret_key";

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        String role = user.getRole();
        String springRole = "ROLE_" + role.toUpperCase();
        claims.put("roles", List.of(springRole));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // หมดอายุ 10 ชั่วโมง
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }


    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (SignatureException | IllegalArgumentException e) {
            // ลายเซ็นต์ไม่ถูกต้อง หรือ token ไม่ถูกต้อง
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // ดึง roles list ออกมาจาก token (assume เก็บใน claim "roles")
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }

    // ฟังก์ชันช่วยดึง claims ทั้งหมดจาก token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
}


