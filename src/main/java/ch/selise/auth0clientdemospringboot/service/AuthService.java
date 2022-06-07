package ch.selise.auth0clientdemospringboot.service;

import ch.selise.auth0clientdemospringboot.config.Auth0Config;
import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.IdTokenValidationException;
import com.auth0.exception.PublicKeyProviderException;
import com.auth0.json.auth.TokenHolder;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.net.TokenRequest;
import com.auth0.utils.tokens.IdTokenVerifier;
import com.auth0.utils.tokens.SignatureVerifier;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;

@Service
public class AuthService {
    private final AuthAPI authAPI;
    private final Auth0Config config;
    public AuthService(AuthAPI authAPI, Auth0Config config) {
        this.authAPI = authAPI;
        this.config = config;
    }

    public TokenHolder login(String username, String password) throws Auth0Exception {
        TokenRequest tokenRequest = authAPI.login(username, password.toCharArray())
                .setScope("openid email");
        TokenHolder tokenHolder = tokenRequest.execute();
        verifyToken(tokenHolder);
        setSecurityContextHolder(tokenHolder.getIdToken());
        return tokenHolder;

    }

    public String authorizeUrl(String redirectUrl, String connection){
        return authAPI.authorizeUrl(redirectUrl)
                .withConnection(connection)
                .withScope("openid contracts")
                .withState("state123")
                .build();
    }

    public String exchangeCode(String code, String redirectUrl) throws Auth0Exception {
        TokenHolder tokenHolder = authAPI.exchangeCode(code, redirectUrl).setScope("openid contacts").execute();
        verifyToken(tokenHolder);
        setSecurityContextHolder(tokenHolder.getIdToken());
        return tokenHolder.getAccessToken();
    }

    private void setSecurityContextHolder(String idToken) {
        DecodedJWT jwt = JWT.decode(idToken);
        TestingAuthenticationToken authToken2 = new TestingAuthenticationToken(jwt.getSubject(), jwt.getToken());
        authToken2.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authToken2);
    }

    public String logout(String logoutUrl){
       return authAPI.logoutUrl(logoutUrl,true)
                .useFederated(true)
                .build();
    }
    private void verifyToken(TokenHolder tokenHolder) throws Auth0Exception {
         JwkProvider provider = new UrlJwkProvider(config.getDomain());
        SignatureVerifier signatureVerifier = SignatureVerifier.forRS256(keyId -> {
            try {
                return (RSAPublicKey) provider.get(keyId).getPublicKey();
            } catch (JwkException e) {
                throw new PublicKeyProviderException("Error obtaining public key", e);
            }
        });
        IdTokenVerifier tokenVerifier = IdTokenVerifier.init(config.getDomain(), config.getClientId(), signatureVerifier).build();
        try{
            tokenVerifier.verify(tokenHolder.getIdToken());
        }catch (IdTokenValidationException exception){
            throw new Auth0Exception(exception.getMessage());
        }

    }
}

