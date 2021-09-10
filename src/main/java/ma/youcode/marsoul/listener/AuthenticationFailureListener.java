package ma.youcode.marsoul.listener;

import ma.youcode.marsoul.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener {

    @Autowired
    private LoginAttemptService loginAttemptService;

    // listener for fail to login
    @EventListener
    public void onAuthenticationFailed(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        // safety check
        if (principal instanceof String) {
            String email = (String) principal;
            loginAttemptService.addUserFromLoginCacheAttempt(email);
        }
    }

}
