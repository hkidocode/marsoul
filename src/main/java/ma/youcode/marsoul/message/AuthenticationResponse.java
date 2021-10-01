package ma.youcode.marsoul.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.youcode.marsoul.config.security.CustomUserDetails;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private HttpHeaders accessToken;
    private CustomUserDetails userDetails;

}
