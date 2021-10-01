package ma.youcode.marsoul.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ma.youcode.marsoul.exception.BadCredentialsException;
import ma.youcode.marsoul.service.LoginAttemptService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final LoadingCache<String, Integer> loginAttemptCache; // String => email | Integer => number of attempts

    // initialize in memory cache
    public LoginAttemptServiceImpl() {
        this.loginAttemptCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(100) // number of users
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }


    @Override
    public void removeUserFromLoginCacheAttempt(String email) {
        loginAttemptCache.invalidate(email);
    }

    @Override
    public void addUserFromLoginCacheAttempt(String email) {
        // get current attempt for that user
        int attempts = 0;
        try {
            int ATTEMPTS_INCREMENT = 1;
            attempts = ATTEMPTS_INCREMENT + loginAttemptCache.get(email);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // update user attempts
        loginAttemptCache.put(email, attempts);
        throw new BadCredentialsException("Email or password is incorrect. Please try again");
    }

    // to check how many user are try to login
    @Override
    public boolean hasExceedMaxAttempts(String email) {
        try {
            int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
            return loginAttemptCache.get(email) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
