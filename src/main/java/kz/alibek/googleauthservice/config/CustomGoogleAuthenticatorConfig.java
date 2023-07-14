package kz.alibek.googleauthservice.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import kz.alibek.googleauthservice.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomGoogleAuthenticatorConfig {

    @Bean
    public GoogleAuthenticator googleAuthenticator(@Autowired CredentialRepository credentialRepository) {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setCredentialRepository(credentialRepository);
        return googleAuthenticator;
    }


}
