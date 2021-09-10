package ma.youcode.marsoul.listener;

import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener {

    @Autowired
    private LoginAttemptService loginAttemptService;

    // listener for success to login
    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        // safety check
        if (principal instanceof User) {
            User user = (User) principal;
            loginAttemptService.removeUserFromLoginCacheAttempt(user.getEmail());
        }
    }

}
