package university.scrapper;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest {

    private final String ra;
    private final String password;
    private static final String userNameField = "desidentificacao";
    private static final String passwordField = "dessenha";

    public LoginRequest(String ra, String password) {
        this.ra = ra;
        this.password = password;
    }

    Map<String, String> asLoginForm() {
        return new HashMap<String, String>() {
            {
                put(userNameField, ra);
                put(passwordField, password);
            }
        };
    }

    public String getRa() {
        return ra;
    }

    public String getPassword() {
        return password;
    }
}
