package ch.selise.auth0clientdemospringboot.controller;

import ch.selise.auth0clientdemospringboot.dto.LoginRequest;
import ch.selise.auth0clientdemospringboot.service.AuthService;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@Controller
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginUi(Model model) {
     model.addAttribute("fromData", new LoginRequest());
     return "login";

    }
    @PostMapping("/login")
    public void login(@ModelAttribute("fromData") LoginRequest loginRequest, Model model, HttpServletResponse response, HttpServletRequest request, BindingResult result) throws IOException {

        if (loginRequest.isLoginWithGoogle()){
            String authorizeUrl = authService.authorizeUrl("http://localhost:8080/api/callback", "google-oauth2");
             response.sendRedirect(authorizeUrl);
        }else {
            TokenHolder tokenHolder = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            model.addAttribute("idToken",tokenHolder.getIdToken());
            response.sendRedirect(getContextPath(request)+"api/dashboard");
        }


    }


    @GetMapping("/callback")
    public String callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        if (Strings.isNotBlank(code)){
            String tokenHolder = authService.exchangeCode(code, getContextPath(request) + "/callback/token");
            System.out.println(tokenHolder);
            return "redirect:/api/dashboard";
        }
       return "login";

    }
    @GetMapping("/callback/token")
    public void callbackToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request);
        System.out.println(response);
         response.sendRedirect(getContextPath(request)+"api/dashboard");
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, final Authentication authentication) throws URISyntaxException, IOException, InterruptedException {

        return "dashboard";
    }
    @GetMapping("/")
    public String home(Model model)  {
        return "index";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response)  {
        String logout = authService.logout(getContextPath(request));
        System.out.println("logout = " + logout);
        return "redirect:"+"/";
    }

    public String getContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
