package pk.habsoft.demo.estore.service;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String generateNewToken();

    void store(String token, Authentication authentication);

    boolean contains(String token);

    Authentication retrieve(String token);

}