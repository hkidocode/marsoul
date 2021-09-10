package ma.youcode.marsoul.service;

public interface LoginAttemptService {
    void removeUserFromLoginCacheAttempt(String email);
    void addUserFromLoginCacheAttempt(String email);
    boolean hasExceedMaxAttempts(String email);
}
