package ma.youcode.marsoul.config.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.marsoul.config.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(CustomUserDetails customUserDetails) {
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .setClaims(setClaims(customUserDetails))
                .setIssuer("Marsoul")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 60 min
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;

    }

    public String getSubjectFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private Map<String, Object> setClaims(CustomUserDetails customUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = customUserDetails.getAuthorities();

        // TODO: Check Auto added roles
        List<String> rolesList = new ArrayList<>();
        rolesList.add("admin");
        rolesList.add("user");
        rolesList.add("manager");

        for (String role: rolesList) {
            if (roles.contains(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))) {
                claims.put(role, true);
            }
        }
        return claims;
    }

}
