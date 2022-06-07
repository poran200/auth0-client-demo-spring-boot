package ch.selise.auth0clientdemospringboot;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class AuthApiCallTest {

    @Test
    void loginTest() throws URISyntaxException, IOException, InterruptedException {
        Map<String, String> prameeter = new HashMap<>();
        prameeter.put("client_id","5XBqskhgP6V3FP0lAU9rYgTIBoQJ5oXD");
        prameeter.put("client_secret","EoJIzDfD1ApJBk9bf80t7e1yBCFjQ0DTCyD8vQUTRb9gSFr4CY9ijNsrEbvwfpbZ");
        prameeter.put("grant_type", "password");
        prameeter.put("username", "abcd@gmail.com");
        prameeter.put("password", "Poran@258369");
        prameeter.put("connection","Username-Password-Authentication");
        prameeter.put("scope", "openid email");
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI("https://dev-osrgs11q.us.auth0.com/oauth/token"))
                .POST(HttpRequest.BodyPublishers.ofString( "{\n" +
                        "    \"client_id\": \"5XBqskhgP6V3FP0lAU9rYgTIBoQJ5oXD\",\n" +
                        "    \"client_secret\": \"EoJIzDfD1ApJBk9bf80t7e1yBCFjQ0DTCyD8vQUTRb9gSFr4CY9ijNsrEbvwfpbZ\",\n" +
                        "    \"grant_type\": \"password\",\n" +
                        "    \"scope\": \"openid email\",\n" +
                        "    \"username\": \"abcd@gmail.com\",\n" +
                        "    \"password\": \"Poran@258369\",\n" +
                        "    \"connection\":\"Username-Password-Authentication\"\n" +
                        "}")).header(HttpHeaders.CONTENT_TYPE,"application/json")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());
    }
}
