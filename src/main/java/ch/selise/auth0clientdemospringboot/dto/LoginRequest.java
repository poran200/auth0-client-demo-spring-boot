package ch.selise.auth0clientdemospringboot.dto;

public class LoginRequest {
    private String username;
    private String password;
    private boolean loginWithGoogle;

    public LoginRequest(String username, String password, boolean singingWithGoogle) {
        this.username = username;
        this.password = password;
        this.loginWithGoogle = singingWithGoogle;
    }

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginWithGoogle() {
        return loginWithGoogle;
    }

    public void setLoginWithGoogle(boolean loginWithGoogle) {
        this.loginWithGoogle = loginWithGoogle;
    }
}
