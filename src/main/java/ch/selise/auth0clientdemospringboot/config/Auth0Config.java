package ch.selise.auth0clientdemospringboot.config;

import com.auth0.client.auth.AuthAPI;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class Auth0Config {
    @Value("${auth0.domain}")
    private String domain;
    @Value("${auth0.clientId}")
    private String clientId;
    @Value("${auth0.clientSecret}")
    private String clientSecret;

    @Bean
    public AuthAPI authAPI(){
        return new AuthAPI(domain,clientId,clientSecret);
    }

}
