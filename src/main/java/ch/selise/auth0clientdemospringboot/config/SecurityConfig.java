package ch.selise.auth0clientdemospringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.Cookie;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/api/login","/api/callback").permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
               .and().logout(logout -> logout
                       .logoutUrl("/api/logout")
                       .logoutSuccessHandler((request, response, authentication) -> {
                           authentication.setAuthenticated(false);
                           response.sendRedirect("http://localhost:8080/");
                       })
                       .addLogoutHandler((request, response, auth) -> {
                           for (Cookie cookie : request.getCookies()) {
                               String cookieName = cookie.getName();
                               Cookie cookieToDelete = new Cookie(cookieName, null);
                               cookieToDelete.setMaxAge(0);
                               response.addCookie(cookieToDelete);
                           }
                       })).build();
    }
}
